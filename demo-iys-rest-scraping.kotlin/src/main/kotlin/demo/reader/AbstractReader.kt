package demo.reader

import com.google.api.client.util.Lists
import com.google.common.collect.Sets
import demo.executor.Executor
import demo.executor.ParallelExecutor
import demo.file.TextFile
import demo.http.HttpRequester
import demo.js.SkillsParser
import demo.json.ProgressFile
import demo.json.TargetsListFile
import demo.model.SourcesList
import demo.model.TargetsList
import org.slf4j.LoggerFactory
import java.io.IOException
import java.util.concurrent.Callable
import java.util.concurrent.atomic.AtomicInteger
import java.util.function.Consumer

abstract class AbstractReader @Throws(Exception::class)
constructor(private val url: String, private val name: String) : Reader {

    private val executor: Executor<SourcesList>
    private val skillsParser: SkillsParser
    private val progressFile: ProgressFile
    private val progress = AtomicInteger(0)
    private val targetsListFile = TargetsListFile(name)
    private val targetsList = targetsListFile.load()
    private val dictionaryFile: TextFile
    private val dictionary: MutableList<String>

    init {
        this.executor = ParallelExecutor<SourcesList>(PARALLEL_EXECUTORS_NUMBER)
        this.skillsParser = SkillsParser()
        this.dictionaryFile = TextFile("reader/dictionary.txt")

        this.progressFile = ProgressFile("progress/progress.json")
        if (this.progressFile.load(name) == null) {
            val combinations = TextFile("combination/combinations1.txt").loadAsList()
            combinations.addAll(TextFile("combination/combinations2.txt").loadAsList())
            this.dictionaryFile.save(combinations)
        }
        this.progressFile.save(name, progress.get())

        this.dictionary = this.dictionaryFile.loadAsList()
    }

    override fun read() {
        val targets = Sets.newCopyOnWriteArraySet(targetsList.targets)

        while (progress.get() < dictionary.size) {
            executor.execute(
                    Callable<SourcesList> {
                        val code = dictionary[progress.andIncrement]
                        logger.info("progress " + progress + "/" + dictionary.size + String.format(" %.3f%% ", 100.toDouble() * progress.get() / dictionary.size) + code)

                        if (code.contains("%")) {
                            SourcesList(Lists.newArrayList(), 0, code)
                        } else {
                            readSources(String.format("&q=%s", code), code)
                        }
                    },
                    Consumer<SourcesList> { sourcesList ->
                        updateTargets(targets, sourcesList)

                        val code = sourcesList.code
                        dictionary.remove(code)

                        if (!code.contains("&page=")) {
                            if (sourcesList.total > sourcesList.sources.size) {
                                val pages = sourcesList.total / sourcesList.sources.size + 1
                                for (page in 2..pages) {
                                    dictionary.add(String.format("%s&page=%d", code, page))
                                }
                            }
                        }
                    }
            )
        }

        logger.info("Reader finished")
    }

    @Throws(IOException::class)
    private fun readSources(suffix: String, code: String): SourcesList {
        logger.debug("request: " + suffix)
        val response = HttpRequester.request(url + suffix)
        logger.debug("response: " + code + " " + response.status + " " + response.total + " " + response.skills)
        return SourcesList(skillsParser.parse(response.skills!!), response.total, code)
    }

    @Synchronized private fun updateTargets(targets: MutableSet<demo.model.Target>, sourcesList: SourcesList) {
        val sizeBefore = targets.size

        if (sourcesList.sources.size > 0) {
            logger.info("returned " + sourcesList.sources.size + "/" + sourcesList.total)
        }

        for (source in sourcesList.sources) {
            targets.add(demo.model.Target(source.id!!.trim(), source.text!!.trim()))
        }

        val sizeAfter = targets.size
        if (sizeAfter > sizeBefore) {
            logger.info("added " + (sizeAfter - sizeBefore))

            progressFile.save(name, progress.get())
            targetsList.updateTargets(targets)
            targetsListFile.save(targetsList)

            dictionaryFile.save(dictionary)
        }
    }

    companion object {

        private val logger = LoggerFactory.getLogger(AbstractReader::class.java)

        private val PARALLEL_EXECUTORS_NUMBER = 10
    }
}
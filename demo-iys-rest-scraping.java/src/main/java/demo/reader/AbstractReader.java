package demo.reader;

import com.google.api.client.util.Lists;
import com.google.common.collect.Sets;
import demo.executor.Executor;
import demo.executor.ParallelExecutor;
import demo.file.TextFile;
import demo.http.HttpRequester;
import demo.js.SkillsParser;
import demo.json.ProgressFile;
import demo.json.TargetsListFile;
import demo.model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

public abstract class AbstractReader implements Reader {

    private static final Logger logger = LoggerFactory.getLogger(AbstractReader.class);

    private static final int PARALLEL_EXECUTORS_NUMBER = 10;

    private final String url;
    private final String name;

    private Executor<SourcesList> executor;
    private SkillsParser skillsParser;
    private ProgressFile progressFile;
    private AtomicInteger progress = new AtomicInteger(0);
    private TargetsListFile targetsListFile;
    private TargetsList targetsList = new TargetsList();
    private TextFile dictionaryFile;
    private List<String> dictionary;

    public AbstractReader(String url, String name) throws Exception {
        this.url = url;
        this.name = name;

        this.executor = new ParallelExecutor<>(PARALLEL_EXECUTORS_NUMBER);
        this.skillsParser = new SkillsParser();
        this.dictionaryFile = new TextFile("reader/dictionary.txt");

        this.progressFile = new ProgressFile("progress/progress.json");
        if (this.progressFile.load(name) == null) {
            List<String> combinations = new TextFile("combination/combinations1.txt").loadAsList();
            combinations.addAll(new TextFile("combination/combinations2.txt").loadAsList());
            this.dictionaryFile.save(combinations);
        }
        this.progressFile.save(name, progress.get());

        this.targetsListFile = new TargetsListFile(name);
        this.targetsList = targetsListFile.load();

        this.dictionary = this.dictionaryFile.loadAsList();
    }

    @Override
    public void read() {
        final Set<Target> targets = Sets.newCopyOnWriteArraySet(targetsList.getTargets());

        while (progress.get() < dictionary.size()) {
            executor.execute(
                    () -> {
                        final String code = dictionary.get(progress.getAndIncrement());
                        logger.info("progress " + progress + "/" + dictionary.size() + String.format(" %.3f%% ", (double) 100 * progress.get() / dictionary.size()) + code);

                        if (code.contains("%")) {
                            return new SourcesList(Lists.newArrayList(), 0, code);
                        } else {
                            return readSources(String.format("&q=%s", code), code);
                        }
                    },
                    sourcesList -> {
                        updateTargets(targets, sourcesList);

                        final String code = sourcesList.getCode();
                        dictionary.remove(code);

                        if (!code.contains("&page=")) {
                            if (sourcesList.getTotal() > sourcesList.getSources().size()) {
                                final int pages = (sourcesList.getTotal() / sourcesList.getSources().size()) + 1;
                                for (int page = 2; page <= pages; page++) {
                                    dictionary.add(String.format("%s&page=%d", code, page));
                                }
                            }
                        }
                    }
            );
        }

        logger.info("Reader finished");
    }

    private SourcesList readSources(String suffix, String code) throws IOException {
        logger.debug("request: " + suffix);
        Response response = HttpRequester.request(url + suffix);
        logger.debug("response: " + code + " " + response.status + " " + response.total + " " + response.skills);
        return new SourcesList(skillsParser.parse(response.skills), response.total, code);
    }

    private synchronized void updateTargets(Set<Target> targets, SourcesList sourcesList) {
        int sizeBefore = targets.size();

        if (sourcesList.getSources().size() > 0) {
            logger.info("returned " + sourcesList.getSources().size() + "/" + sourcesList.getTotal());
        }

        for (Source source : sourcesList.getSources()) {
            targets.add(new Target(source.id.trim(), source.text.trim()));
        }

        int sizeAfter = targets.size();
        if (sizeAfter > sizeBefore) {
            logger.info("added " + (sizeAfter - sizeBefore));

            progressFile.save(name, progress.get());
            targetsList.updateTargets(targets);
            targetsListFile.save(targetsList);

            dictionaryFile.save(dictionary);
        }
    }
}
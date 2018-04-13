package demo.file

import com.google.common.base.Charsets
import com.google.common.base.Joiner
import com.google.common.base.Splitter
import com.google.common.collect.Lists
import com.google.common.io.Files
import java.io.File
import java.io.IOException

class TextFile(fileName: String) {

    private val fileName: String

    init {
        this.fileName = "src/main/resources/" + fileName
    }

    fun load(): String {
        try {
            val file = File(fileName)
            val source = Files.asCharSource(file, Charsets.UTF_8)
            return source.read()
        } catch (e: IOException) {
            throw RuntimeException("Exception during loading the file: " + fileName, e)
        }

    }

    fun loadAsIterable(): Iterable<String> {
        return Splitter.on('\n').trimResults().omitEmptyStrings().split(load())
    }

    fun loadAsList(): MutableList<String> {
        return Lists.newArrayList(loadAsIterable())
    }

    fun save(value: String) {
        try {
            val file = File(fileName)
            val sink = Files.asCharSink(file, Charsets.UTF_8)
            sink.write(value)
        } catch (e: IOException) {
            throw RuntimeException("Exception during saving the file: " + fileName, e)
        }

    }

    fun save(values: List<String>) {
        save(Joiner.on('\n').join(values))
    }
}
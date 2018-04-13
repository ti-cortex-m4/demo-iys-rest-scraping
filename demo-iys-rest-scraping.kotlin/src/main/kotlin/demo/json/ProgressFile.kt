package demo.json

import com.google.common.collect.Maps
import com.google.common.reflect.TypeToken
import com.google.gson.GsonBuilder
import demo.file.TextFile

class ProgressFile(fileName: String) {

    private val textFile: TextFile

    init {
        this.textFile = TextFile(fileName)
    }

    private fun loadAll(): MutableMap<String, Int> {
        val map: MutableMap<String, Int> = GSON.fromJson(textFile.load(), TYPE)
        return map ?: Maps.newHashMap()
    }

    fun load(key: String): Int? {
        val map = loadAll()
        return map[key]
    }

    fun save(key: String, value: Int) {
        val map = loadAll()
        map.put(key, value)
        textFile.save(GSON.toJson(map, TYPE))
    }

    companion object {

        private val GSON = GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create()
        private val TYPE = object : TypeToken<MutableMap<String, Int>>() {
        }.type
    }
}

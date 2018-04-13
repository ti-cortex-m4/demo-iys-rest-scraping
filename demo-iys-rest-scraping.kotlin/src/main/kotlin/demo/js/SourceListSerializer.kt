package demo.js

import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import demo.model.Source

internal object SourceListSerializer {

    private val GSON = GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create()
    private val TYPE = object : TypeToken<List<Source>>() {
    }.type

    fun fromJson(json: String): List<Source> {
        return GSON.fromJson(json, TYPE)
    }
}

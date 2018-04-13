package demo.json

import com.google.gson.GsonBuilder
import demo.model.TargetsList

internal object TargetsListSerializer {

    private val GSON = GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create()

    fun fromJson(json: String): TargetsList {
        return GSON.fromJson(json, TargetsList::class.java)
    }

    fun toJson(targetsList: TargetsList): String {
        return GSON.toJson(targetsList)
    }
}

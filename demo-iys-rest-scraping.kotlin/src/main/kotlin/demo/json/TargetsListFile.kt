package demo.json

import demo.file.TextFile
import demo.model.TargetsList

class TargetsListFile(fileName: String) {

    private val textFile: TextFile

    init {
        this.textFile = TextFile(fileName)
    }

    fun load(): TargetsList {
        return TargetsListSerializer.fromJson(textFile.load())
    }

    fun save(targetsList: TargetsList) {
        textFile.save(TargetsListSerializer.toJson(targetsList))
    }
}

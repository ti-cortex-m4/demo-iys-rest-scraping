package demo.file

import demo.json.TargetsListFile
import demo.model.Target
import demo.model.TargetsList
import java.util.*
import java.util.function.Function

object TsvFile {

    @JvmStatic fun main(args: Array<String>) {
        val fileName = "reader/skills.json"
        val targetsListFile = TargetsListFile(fileName)
        val targetsList = targetsListFile.load()

        saveSortById(targetsList, fileName)
        saveSortByName(targetsList, fileName)
    }

    private fun saveSortById(targetsList: TargetsList, fileName: String) {
        save(
                targetsList, fileName, ".sort by id.tsv",
                Comparator<Target> { o1, o2 -> o1.id.compareTo(o2.id) },
                Function<Target, String> { o -> o.id + '\t' + o.name }
        )
    }

    private fun saveSortByName(targetsList: TargetsList, fileName: String) {
        save(
                targetsList, fileName, ".sort by name.tsv",
                Comparator<Target> { o1, o2 -> o1.name.compareTo(o2.name) },
                Function<Target, String> { o -> o.name + '\t' + o.id }
        )
    }

    private fun save(targetsList: TargetsList, fileName: String, extension: String, comparator: Comparator<Target>, mapper: Function<Target, String>) {
        val value: List<String> = targetsList.targets
                .sortedWith(comparator)
                .map { target -> mapper.apply(target) }

        val textFile = TextFile(fileName.replace(".json", extension))
        textFile.save(value)
    }
}

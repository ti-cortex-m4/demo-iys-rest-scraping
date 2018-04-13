package demo.file;

import demo.json.TargetsListFile;
import demo.model.Target;
import demo.model.TargetsList;

import java.util.Comparator;
import java.util.function.Function;
import java.util.stream.Collectors;

public class TsvFile {

    public static void main(String[] args) {
        final String fileName = "reader/skills.json";
        TargetsListFile targetsListFile = new TargetsListFile(fileName);
        TargetsList targetsList = targetsListFile.load();

        saveSortById(targetsList, fileName);
        saveSortByName(targetsList, fileName);
    }

    private static void saveSortById(TargetsList targetsList, String fileName) {
        save(
                targetsList, fileName, ".sort by id.tsv",
                (o1, o2) -> o1.getId().compareTo(o2.getId()),
                (o) -> o.getId() + '\t' + o.getName()
        );
    }

    private static void saveSortByName(TargetsList targetsList, String fileName) {
        save(
                targetsList, fileName, ".sort by name.tsv",
                (o1, o2) -> o1.getName().compareTo(o2.getName()),
                (o) -> o.getName() + '\t' + o.getId()
        );
    }

    private static void save(TargetsList targetsList, String fileName, String extension, Comparator<Target> comparator, Function<Target, String> mapper) {
        String value = targetsList.getTargets()
                .stream()
                .sorted(comparator)
                .map(mapper)
                .collect(Collectors.joining("\n"));

        TextFile textFile = new TextFile(fileName.replace(".json", extension));
        textFile.save(value);
    }
}

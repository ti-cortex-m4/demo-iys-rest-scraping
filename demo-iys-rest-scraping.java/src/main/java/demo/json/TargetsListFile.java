package demo.json;

import demo.file.TextFile;
import demo.model.TargetsList;

public class TargetsListFile {

    private final TextFile textFile;

    public TargetsListFile(String fileName) {
        this.textFile = new TextFile(fileName);
    }

    public TargetsList load() {
        return TargetsListSerializer.fromJson(textFile.load());
    }

    public void save(TargetsList targetsList) {
        textFile.save(TargetsListSerializer.toJson(targetsList));
    }
}

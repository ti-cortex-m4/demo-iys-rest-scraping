package demo.json;

import com.google.common.collect.Maps;
import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import demo.file.TextFile;

import java.lang.reflect.Type;
import java.util.Map;

public class ProgressFile {

    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create();
    private static final Type TYPE = new TypeToken<Map<String, Integer>>() {}.getType();

    private final TextFile textFile;

    public ProgressFile(String fileName) {
        this.textFile = new TextFile(fileName);
    }

    private Map<String, Integer> loadAll() {
        Map<String, Integer> map = GSON.fromJson(textFile.load(), TYPE);
        return (map != null) ? map : Maps.newHashMap();
    }

    public Integer load(String key) {
        Map<String, Integer> map = loadAll();
        return map.get(key);
    }

    public void save(String key, Integer value) {
        Map<String, Integer> map = loadAll();
        map.put(key, value);
        textFile.save(GSON.toJson(map, TYPE));
    }
}

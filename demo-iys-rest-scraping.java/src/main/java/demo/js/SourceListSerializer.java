package demo.js;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import demo.model.Source;

import java.lang.reflect.Type;
import java.util.List;

class SourceListSerializer {

    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create();
    private static final Type TYPE = new TypeToken<List<Source>>() {}.getType();

    static List<Source> fromJson(String json) {
        return GSON.fromJson(json, TYPE);
    }
}

package demo.json;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import demo.model.TargetsList;

class TargetsListSerializer {

    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create();

    static TargetsList fromJson(String json) {
        return GSON.fromJson(json, TargetsList.class);
    }

    static String toJson(TargetsList targetsList) {
        return GSON.toJson(targetsList);
    }
}

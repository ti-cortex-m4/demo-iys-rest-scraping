package demo.js;

import demo.model.Source;
import jdk.nashorn.api.scripting.ScriptObjectMirror;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.List;

public class SkillsParser {

    private final ScriptObjectMirror scriptObjectMirror;

    public SkillsParser() throws FileNotFoundException, ScriptException {
        ScriptEngine scriptEngine = new ScriptEngineManager().getEngineByName("nashorn");
        scriptEngine.eval(new FileReader("src/main/resources/js/iysx.js"));

        scriptObjectMirror = (ScriptObjectMirror) scriptEngine.eval("IYSX");
    }

    public synchronized List<Source> parse(String skills) {
        String json = (String) scriptObjectMirror.callMember("decompress", skills);
        return SourceListSerializer.fromJson(json);
    }
}

package demo.js

import demo.model.Source
import jdk.nashorn.api.scripting.ScriptObjectMirror
import java.io.FileNotFoundException
import java.io.FileReader
import javax.script.ScriptEngineManager
import javax.script.ScriptException

class SkillsParser @Throws(FileNotFoundException::class, ScriptException::class)
constructor() {

    private val scriptObjectMirror: ScriptObjectMirror

    init {
        val scriptEngine = ScriptEngineManager().getEngineByName("nashorn")
        scriptEngine.eval(FileReader("src/main/resources/js/iysx.js"))

        scriptObjectMirror = scriptEngine.eval("IYSX") as ScriptObjectMirror
    }

    @Synchronized fun parse(skills: String): List<Source> {
        val json = scriptObjectMirror.callMember("decompress", skills) as String
        return SourceListSerializer.fromJson(json)
    }
}

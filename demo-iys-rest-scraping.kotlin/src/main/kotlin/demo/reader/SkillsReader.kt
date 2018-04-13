package demo.reader

class SkillsReader @Throws(Exception::class)
constructor() : AbstractReader(SkillsReader.URL, "reader/skills.json") {
    companion object {

        private const val URL_PREFIX = "https://skill.itsyourskills.com"
        private const val URL = URL_PREFIX + "/skill-data?search=1&tk=CCC%1A%5D%40GM%5BAFG_%5DXXG%1AW%5BYttty%0Cw%05%02%04%00%07%07"
    }
}
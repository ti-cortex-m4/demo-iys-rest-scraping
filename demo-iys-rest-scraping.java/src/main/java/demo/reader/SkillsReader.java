package demo.reader;

public class SkillsReader extends AbstractReader {

    private static final String URL_PREFIX = "https://skill.itsyourskills.com";
    private static final String URL = URL_PREFIX + "/skill-data?search=1&tk=CCC%1A%5D%40GM%5BAFG_%5DXXG%1AW%5BYttty%0Cw%05%02%04%00%07%07";

    public SkillsReader() throws Exception {
        super(URL, "reader/skills.json");
    }
}
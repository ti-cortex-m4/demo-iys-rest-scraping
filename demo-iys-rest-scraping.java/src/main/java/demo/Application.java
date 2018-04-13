package demo;

import demo.reader.Reader;
import demo.reader.SkillsReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Application {

    private static final Logger logger = LoggerFactory.getLogger(Application.class);

    public static void main(String[] args) throws Exception {
        logger.info("application started");

        Reader reader = new SkillsReader();
        reader.read();

        logger.info("application finished");
    }
}

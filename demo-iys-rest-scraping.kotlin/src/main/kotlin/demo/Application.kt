package demo

import demo.reader.SkillsReader
import org.slf4j.LoggerFactory

object Application {

    private val logger = LoggerFactory.getLogger(Application::class.java)

    @Throws(Exception::class)
    @JvmStatic fun main(args: Array<String>) {
        logger.info("application started")

        val reader = SkillsReader()
        reader.read()

        logger.info("application finished")
    }
}

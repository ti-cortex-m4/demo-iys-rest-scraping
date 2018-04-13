package demo.http

import com.google.common.collect.ImmutableList
import demo.file.TextFile
import java.util.*

internal object RandomUserAgent {

    private val USER_AGENTS: ImmutableList<String>
    private val RANDOM = Random()

    init {
        USER_AGENTS = ImmutableList.copyOf(TextFile("http/UserAgents.txt").loadAsIterable())
    }

    fun getRandomUserAgent(): String = USER_AGENTS[RANDOM.nextInt(USER_AGENTS.size)]
}

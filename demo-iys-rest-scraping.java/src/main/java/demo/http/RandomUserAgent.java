package demo.http;

import com.google.common.collect.ImmutableList;
import demo.file.TextFile;

import java.util.Random;

class RandomUserAgent {

    private static final ImmutableList<String> USER_AGENTS;
    private static final Random RANDOM = new Random();

    static {
        USER_AGENTS = ImmutableList.copyOf(new TextFile("http/UserAgents.txt").loadAsIterable());
    }

    static String getRandomUserAgent() {
        return USER_AGENTS.get(RANDOM.nextInt(USER_AGENTS.size()));
    }
}

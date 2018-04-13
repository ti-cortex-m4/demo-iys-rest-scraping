package demo.combination;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import demo.file.TextFile;

import java.util.List;

public class Combinator {

    private static final ImmutableList<Character> CHARS = Lists.charactersOf("!\"#$%&'()*+,-./0123456789:;<=>?@[\\]^_`abcdefghijklmnopqrstuvwxyz{|}~");

    public static void main(String[] args) {
        saveCombinations1();
        saveCombinations2();
        saveCombinations3();
    }

    private static void saveCombinations1() {
        List<String> combinations = Lists.newArrayList();

        for (char a : CHARS) {
            combinations.add(of(a));
        }

        save("combination/combinations1.txt", combinations);
    }

    private static void saveCombinations2() {
        List<String> combinations = Lists.newArrayList();

        for (char a : CHARS) {
            for (char b : CHARS) {
                combinations.add(of(a) + of(b));
            }
        }

        save("combination/combinations2.txt", combinations);
    }

    private static void saveCombinations3() {
        List<String> combinations = Lists.newArrayList();

        for (char a : CHARS) {
            for (char b : CHARS) {
                for (char c : CHARS) {
                    combinations.add(of(a) + of(b) + of(c));
                }
            }
        }

        save("combination/combinations3.txt", combinations);
    }

    private static String of(char c) {
        return String.valueOf(c);
    }

    private static void save(String fileName, List<String> combinations) {
        new TextFile(fileName).save(combinations);
    }
}

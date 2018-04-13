package demo.combination

import com.google.common.collect.Lists
import demo.file.TextFile

object Combinator {

    private val CHARS = Lists.charactersOf("!\"#$%&'()*+,-./0123456789:;<=>?@[\\]^_`abcdefghijklmnopqrstuvwxyz{|}~")

    @JvmStatic fun main(args: Array<String>) {
        saveCombinations1()
        saveCombinations2()
        saveCombinations3()
    }

    private fun saveCombinations1() {
        val combinations = Lists.newArrayList<String>()

        for (a in CHARS) {
            combinations.add(of(a))
        }

        save("combination/combinations1.txt", combinations)
    }

    private fun saveCombinations2() {
        val combinations = Lists.newArrayList<String>()

        for (a in CHARS) {
            for (b in CHARS) {
                combinations.add(of(a) + of(b))
            }
        }

        save("combination/combinations2.txt", combinations)
    }

    private fun saveCombinations3() {
        val combinations = Lists.newArrayList<String>()

        for (a in CHARS) {
            for (b in CHARS) {
                for (c in CHARS) {
                    combinations.add(of(a) + of(b) + of(c))
                }
            }
        }

        save("combination/combinations3.txt", combinations)
    }

    private fun of(c: Char): String {
        return c.toString()
    }

    private fun save(fileName: String, combinations: List<String>) {
        TextFile(fileName).save(combinations)
    }
}

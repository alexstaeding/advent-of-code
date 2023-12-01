package aoc

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe

private val spec: FunSpec.() -> Unit = {

    test("basicExampleA") {
        """
            Sabqponm
            abcryxxl
            accszExk
            acctuvwj
            abdefghi
        """.trimIndent().day12a() shouldBe 31
    }
    test("inputFileA") {
        getInput(12).day12a() shouldBe 394
    }
    test("basicExampleB") {
        """
            Sabqponm
            abcryxxl
            accszExk
            acctuvwj
            abdefghi
        """.trimIndent().day12b() shouldBe 29
    }
    test("inputFileB") {
        getInput(12).day12b() shouldBe 388
    }
}

class Day12Test : FunSpec(spec)

package aoc

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe

class Day8Test : FunSpec({
    test("inputFileA") {
        getInput(8).day8a() shouldBe 1835
    }
    test("inputFileB") {
        getInput(8).day8b() shouldBe 263670
    }
})

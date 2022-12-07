package aoc

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe

class Day7Test : FunSpec({
    test("inputFileA") {
        getInput(7).day7a() shouldBe 1644735
    }
    test("inputFileB") {
        getInput(7).day7b() shouldBe 1300850
    }
})

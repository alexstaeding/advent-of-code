package aoc

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe

class Day6Test : FunSpec({
    test("inputFileA") {
        getInput(6).day6a() shouldBe 1275
    }
    test("inputFileB") {
        getInput(6).day6b() shouldBe 3605
    }
})

package aoc

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe

/**
 * How many ranges contain the adjacent range?
 */
class Day4Test : FunSpec({
    test("verySimpleNoContains") {
        "4-8,3-7".day4() shouldBe 0
    }
    test("verySimpleContains") {
        "2-8,3-7".day4() shouldBe 1
    }
})

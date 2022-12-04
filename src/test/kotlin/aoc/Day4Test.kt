package aoc

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe

/**
 * How many ranges contain the adjacent range?
 */
class Day4Test : FunSpec({
    test("verySimpleNoContains") {
        "4-8,3-7".day4a() shouldBe 0
    }
    test("verySimpleContains") {
        "2-8,3-7".day4a() shouldBe 1
    }
    test("advancedA1") {
        """
            22-77,14-96
            7-99,65-98
            22-36,37-62
            25-94,24-67
            6-91,1-6
            88-88,2-88
            27-89,58-96
            21-82,9-94
            38-79,27-80
            4-90,30-55
            84-97,52-93
            12-59,11-12
            4-14,5-12
            50-84,49-89
            3-98,1-3
            91-95,8-22
        """.trimIndent().day4a() shouldBe 8
    }
    test("inputFileA") {
        getInput(4).day4a() shouldBe 532
    }
    test("inputFileB") {
        getInput(4).day4b() shouldBe 854
    }
})

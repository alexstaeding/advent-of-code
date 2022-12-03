package aoc

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe

class Day3Test : FunSpec({

    test("toScore") {
        'a'.toScore() shouldBe 1
        'A'.toScore() shouldBe 1 + 26
        'b'.toScore() shouldBe 2
        'B'.toScore() shouldBe 2 + 26
        'p'.toScore() shouldBe 16
        'P'.toScore() shouldBe 16 + 26
        'z'.toScore() shouldBe 26
        'Z'.toScore() shouldBe 26 + 26
    }

    test("testVerySimpleA") {
        """
            vJrwpWtwJgWrhcsFMMfFFhFp
        """.trimIndent().day3() shouldBe 'p'.toScore()
    }
    test("testVerySimpleB") {
        """
            jqHRNqRjqzjGDLGLrsFMfFZSrLrFZsSL
        """.trimIndent().day3() shouldBe 'L'.toScore()
    }
    test("testBasicMultiLine") {
        """
            vJrwpWtwJgWrhcsFMMfFFhFp
            jqHRNqRjqzjGDLGLrsFMfFZSrLrFZsSL
            PmmdzqPrVvPwwTWBwg
            wMqvLMZHhHMvwLHjbvcjnnSBnvTQFn
            ttgJtRGJQctTZtZT
            CrZsJsPPZsGzwwsLwLmpwMDw
        """.trimIndent().trimEnd().day3() shouldBe sequenceOf(
            'p',
            'L',
            'P',
            'v',
            't',
            's',
        ).sumOf { it.toScore() }

    }
})

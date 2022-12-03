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

    test("singleLineA1") {
        """
            vJrwpWtwJgWrhcsFMMfFFhFp
        """.trimIndent().day3a() shouldBe 'p'.toScore()
    }

    test("verySimpleA2") {
        """
            jqHRNqRjqzjGDLGLrsFMfFZSrLrFZsSL
        """.trimIndent().day3a() shouldBe 'L'.toScore()
    }

    test("basicMultiLineA") {
        """
            vJrwpWtwJgWrhcsFMMfFFhFp
            jqHRNqRjqzjGDLGLrsFMfFZSrLrFZsSL
            PmmdzqPrVvPwwTWBwg
            wMqvLMZHhHMvwLHjbvcjnnSBnvTQFn
            ttgJtRGJQctTZtZT
            CrZsJsPPZsGzwwsLwLmpwMDw
        """.trimIndent().trimEnd().day3a() shouldBe sequenceOf(
            'p',
            'L',
            'P',
            'v',
            't',
            's',
        ).sumOf { it.toScore() }
    }

    test("inputFileA") {
        getInput(3).day3a() shouldBe 7997
    }

    test("basicMultiLineB") {
        """
            vJrwpWtwJgWrhcsFMMfFFhFp
            jqHRNqRjqzjGDLGLrsFMfFZSrLrFZsSL
            PmmdzqPrVvPwwTWBwg
        """.trimIndent().trimEnd().day3b() shouldBe 18
    }

    test("inputFileB") {
        getInput(3).day3b() shouldBe 2545
    }
})

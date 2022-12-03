package aoc

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe

/**
 * A basic rock-paper-scissors game.
 *
 * The left column is the opponent's hand, the right column is the outcome.
 *
 * A = rock
 * B = paper
 * C = scissors
 *
 * X = lose
 * Y = draw
 * Z = win
 */
class Day2Test : FunSpec({
    test("verySimple") {
        """
            A Y
            B X
            C Z
        """.trimIndent().day2() shouldBe 12
    }

    test("advancedA") {
        """
            B Z
            C X
            C X
            C X
            B Y
            A X
            B Y
            B X
            A Y
            B X
            B X
            A X
            C Y
            B X
            A X
            A X
        """.trimIndent().day2() shouldBe 51
    }

    test("advancedB") {
        """
            A X
            A X
            A X
            A Y
            A X
            A Z
            C X
            B Y
            B X
            A X
            B X
            C X
            A X
            A X
            B X
            A X
            A X
            A X
            A X
            B X
            A X
            C X
            A Y
            B X
            C X
            A X
            B X
            A X
            B Y
            A X
            C Y
            A X
            C Y
            C X
            B Y
            A Y
            A X
            A X
            B X
            A X
            C X
        """.trimIndent().day2() shouldBe 123
    }

    test("advancedC") {
        """
            A Y
            A X
            A Y
            A X
            A Y
            B Z
            A X
            B Y
            B Z
            B X
            C X
            A X
            A X
            C X
            A X
            A X
            A Y
            B X
            C X
            A X
            A Z
            C X
            B Y
            B X
            A X
            B X
            C X
            A X
            A X
            B X
            A X
            A X
            A X
            A X
            B X
            A X
            C X
            B X
            C X
            C X
            B X
            A Y
            A X
            A Y
            A X
            A X
            A X
            A X
            C Y
            B X
            A X
            A X
            A X
            B Y
            A X
            B X
            B Y
            A Z
            A Y
            C X
            C Y
            A X
            A X
            A X
            B X
            A Y
            C X
            A X
            B Y
            A Y
            B X
            B X
            A X
            A Y
            B X
            A Y
            C X
            A X
            C X
            A X
            C X
            A X
            A X
            C X
            A X
            A X
            A Y
            B X
            C X
            B Y
            A Y
            A X
            A X
            B X
            A X
            C X
        """.trimIndent().day2() shouldBe 293
    }

    test("advancedD") {
        """
            B Y
            A X
            A X
            A Z
            C Y
            C Y
            B X
            C Z
            A X
            A X
            A X
            B Z
            B Y
            A X
            A X
            A X
            B X
            A X
            C X
            C X
            C X
            B X
            A X
            A X
            C Z
            B Z
            C Y
            A X
            A X
            A X
            C Z
            A X
            A X
            C X
            C X
            A X
            C Y
            A X
            A Y
            A X
            A X
            A Z
            C X
            C X
            A Y
            B X
            B X
            A Y
            A X
            B X
            C Y
            A X
            A Y
            B Z
            A X
            C Y
            A X
            B Y
            A X
            A Y
            A X
            C Z
            A X
            B X
            A X
            B Y
            A X
            A X
            A X
            A X
        """.trimIndent().day2() shouldBe 264
    }

    test("inputFile") {
        getInput(2).day2() shouldBe 8295
    }
})

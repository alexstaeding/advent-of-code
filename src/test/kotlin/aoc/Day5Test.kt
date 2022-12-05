package aoc

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe

class Day5Test : FunSpec({

    test("readState1") {
        """
            [A]
             1
        """.trimIndent().lines().readState() shouldBe listOf(listOf('A'))
    }

    test("readState2") {
        """
            [A] [B]
             1   2
        """.trimIndent().lines().readState() shouldBe listOf(listOf('A'), listOf('B'))
    }

    test("readState3") {
        """
            [D]
            [A] [B] [C]
             1   2   3
        """.trimIndent().lines().readState() shouldBe listOf(listOf('A', 'D'), listOf('B'), listOf('C'))
    }

    test("readState4") {
        """
                [D]
            [A] [B] [C]
             1   2   3
        """.trimIndent().lines().readState() shouldBe listOf(listOf('A'), listOf('B', 'D'), listOf('C'))
    }

    test("readState5") {
        """
                    [D]
            [A] [B] [C]
             1   2   3
        """.trimIndent().lines().readState() shouldBe listOf(listOf('A'), listOf('B'), listOf('C', 'D'))
    }

    test("testSimple") {
        """
                [D]
            [N] [C]
            [Z] [M] [P]
             1   2   3

            move 1 from 2 to 1
            move 3 from 1 to 3
            move 2 from 2 to 1
            move 1 from 1 to 2
        """.trimIndent().day5a() shouldBe "CMZ"
    }

    test("inputFileA") {
        getInput(5).day5a() shouldBe "TWSGQHNHL"
    }
})

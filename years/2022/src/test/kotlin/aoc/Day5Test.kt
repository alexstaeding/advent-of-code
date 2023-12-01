package aoc

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe

private val spec: FunSpec.() -> Unit = {

    test("readState1") {
        """
            [A]
        """.trimIndent().lines().readState() shouldBe listOf(listOf('A'))
    }

    test("readState2") {
        """
            [A] [B]
        """.trimIndent().lines().readState() shouldBe listOf(listOf('A'), listOf('B'))
    }

    test("readState3") {
        """
            [D]
            [A] [B] [C]
        """.trimIndent().lines().readState() shouldBe listOf(listOf('A', 'D'), listOf('B'), listOf('C'))
    }

    test("readState4") {
        """
                [D]
            [A] [B] [C]
        """.trimIndent().lines().readState() shouldBe listOf(listOf('A'), listOf('B', 'D'), listOf('C'))
    }

    test("readState5") {
        """
                    [D]
            [A] [B] [C]
        """.trimIndent().lines().readState() shouldBe listOf(listOf('A'), listOf('B'), listOf('C', 'D'))
    }

    mapOf(
        String::day5a to "CMZ",
        String::day5b to "MCD",
    ).forEach { (func, expected) ->
        test("simple${func.name[4].uppercaseChar()}") {
            """
                [D]
            [N] [C]
            [Z] [M] [P]
             1   2   3

            move 1 from 2 to 1
            move 3 from 1 to 3
            move 2 from 2 to 1
            move 1 from 1 to 2
            """.trimIndent().(func as String.() -> String)() shouldBe expected
        }
    }

    test("inputFileA") {
        getInput(5).day5a() shouldBe "TWSGQHNHL"
    }

    test("inputFileB") {
        getInput(5).day5b() shouldBe "JNRSCDWPP"
    }
}

class Day5Test : FunSpec(spec)

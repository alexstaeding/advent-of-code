package aoc

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe

class Day1Test : FunSpec({
    test("getLargest") {
        """
            1
            2
            3

            4
            5
            6

            2

            1
        """.trimIndent().day1().take(3).toList() shouldBe listOf(15, 6, 2)
    }
})

package aoc

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe

class Day3Test : FunSpec({
    test("testVerySimpleA") {
        """
            vJrwpWtwJgWrhcsFMMfFFhFp
        """.trimIndent().day3() shouldBe 16
    }
})

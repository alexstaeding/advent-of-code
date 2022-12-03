package aoc

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe

class Day2Test : FunSpec({
    test("calculatePlayerPoints") {
        val input = """
            A Y
            B X
            C Z
        """.trimIndent()
        input.splitToSequence("\n")
            .map { it.toGame() }
            .sumOf { it.calculatePlayerPoints() } shouldBe 12
    }
})

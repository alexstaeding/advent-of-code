package aoc

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe

private val spec: FunSpec.() -> Unit = {
    test("inputFileExample") {
        Framework.getInput(1, useExample = true).lineSequence().day1a() shouldBe 11
    }
    test("inputFile") {
        Framework.getInput(1, useExample = false).lineSequence().day1a() shouldBe 1722302
    }
}

class Day1Test : FunSpec(spec)

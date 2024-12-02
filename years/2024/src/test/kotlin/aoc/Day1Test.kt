package aoc

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe

private val spec: FunSpec.() -> Unit = {
    test("inputFileExampleA") {
        Framework.getInput(1, useExample = true).lineSequence().day1a() shouldBe 11
    }
    test("inputFileExampleA") {
        Framework.getInput(1, useExample = true).lineSequence().day1b() shouldBe 31
    }
    test("inputFileA") {
        Framework.getInput(1, useExample = false).lineSequence().day1a() shouldBe 1722302
    }
    test("inputFileB") {
        Framework.getInput(1, useExample = false).lineSequence().day1b() shouldBe 20373490
    }
}

class Day1Test : FunSpec(spec)

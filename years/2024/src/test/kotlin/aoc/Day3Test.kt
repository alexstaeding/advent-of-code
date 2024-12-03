package aoc

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe

private val spec: FunSpec.() -> Unit = {
    test("inputFileExampleA") {
        Framework.getInput(3, "a", useExample = true).lineSequence().day3a() shouldBe 161
    }
    test("inputFileExampleB") {
        Framework.getInput(3, "b", useExample = true).lineSequence().day3b() shouldBe 48
    }
    test("inputFileA") {
        Framework.getInput(3, useExample = false).lineSequence().day3a() shouldBe 173731097
    }
    test("inputFileB") {
        Framework.getInput(3, useExample = false).lineSequence().day3b() shouldBe 173731097
    }
}

class Day3Test : FunSpec(spec)

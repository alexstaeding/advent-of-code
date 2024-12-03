package aoc

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe

private val spec: FunSpec.() -> Unit = {
    test("inputFileExampleA") {
        Framework.getInput(3, "a", useExample = true).readText().day3a() shouldBe 161
    }
    test("inputFileExampleB") {
        Framework.getInput(3, "b", useExample = true).readText().day3b() shouldBe 48
    }
    test("inputFileA") {
        Framework.getInput(3, useExample = false).readText().day3a() shouldBe 173731097
    }
    test("inputFileB") {
        Framework.getInput(3, useExample = false).readText().day3b() shouldBe 93729253
    }
}

class Day3Test : FunSpec(spec)

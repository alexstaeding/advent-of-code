package aoc

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe

private val spec: FunSpec.() -> Unit = {
    test("inputFileExampleA") {
        Framework.getInput(2, useExample = true).lineSequence().day2a() shouldBe 8
    }
    test("inputFileExampleB") {
        Framework.getInput(2, useExample = true).lineSequence().day2b() shouldBe 2286
    }
    test("inputFileA") {
        Framework.getInput(2).lineSequence().day2a() shouldBe 2447
    }
    test("inputFileB") {
        Framework.getInput(2).lineSequence().day2b() shouldBe 56322
    }
}

class Day2Test : FunSpec(spec)

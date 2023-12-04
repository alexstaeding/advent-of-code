package aoc

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe

private val spec: FunSpec.() -> Unit = {
    test("inputFileExampleA") {
        Framework.getInput(4, useExample = true).lineSequence().day4a() shouldBe 13
    }
    test("inputFileExampleB") {
        Framework.getInput(4, useExample = true).readText().day4b() shouldBe 30
    }
    test("inputFileA") {
        Framework.getInput(4).lineSequence().day4a() shouldBe 19135
    }
    test("inputFileB") {
        Framework.getInput(4).readText().day4b() shouldBe 5704953
    }
}

class Day4Test : FunSpec(spec)

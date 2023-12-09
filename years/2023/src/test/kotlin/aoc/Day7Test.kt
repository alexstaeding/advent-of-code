package aoc

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe

private val spec: FunSpec.() -> Unit = {
    test("inputFileExampleA") {
        Framework.getInput(7, useExample = true).lineSequence().day7a() shouldBe 6440
    }
    test("inputFileExampleB") {
        Framework.getInput(7, useExample = true).lineSequence().day7b() shouldBe 5905
    }
    test("inputFileA") {
        Framework.getInput(7).lineSequence().day7a() shouldBe 246424613
    }
    test("inputFileB") {
        Framework.getInput(7).lineSequence().day7b() shouldBe 248256639
    }
}

class Day7Test : FunSpec(spec)

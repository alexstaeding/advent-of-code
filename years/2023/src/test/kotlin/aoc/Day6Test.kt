package aoc

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe

private val spec: FunSpec.() -> Unit = {
    test("inputFileExampleA") {
        Framework.getInput(6, useExample = true).lineSequence().day6a() shouldBe 288
    }
    test("inputFileExampleB") {
        Framework.getInput(6, useExample = true).lineSequence().day6b() shouldBe 71503
    }
    test("inputFileA") {
        Framework.getInput(6).getReader().lineSequence() shouldBe 2344708
    }
    test("inputFileB") {
        Framework.getInput(6).getReader().lineSequence() shouldBe 30125202
    }
}

class Day6Test : FunSpec(spec)

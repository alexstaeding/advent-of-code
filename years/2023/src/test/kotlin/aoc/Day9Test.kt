package aoc

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe

private val spec: FunSpec.() -> Unit = {
    test("inputFileExampleA") {
        Framework.getInput(9, useExample = true).lineSequence().day9a() shouldBe 114L
    }
    test("inputFileExampleB") {
        Framework.getInput(9, useExample = true).lineSequence().day9b() shouldBe 2L
    }
    test("inputFileA") {
        Framework.getInput(9).lineSequence().day9a() shouldBe 1702218515L
    }
    test("inputFileB") {
        Framework.getInput(9).lineSequence().day9b() shouldBe 925L
    }
}

class Day9Test : FunSpec(spec)

package aoc

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe

private val spec: FunSpec.() -> Unit = {
    test("inputFileExampleA") {
        Framework.getInput(3, useExample = true).readText().day3a() shouldBe 4361
    }
    test("inputFileExampleB") {
        Framework.getInput(3, useExample = true).readText().day3b() shouldBe 467835
    }
    test("inputFileA") {
        Framework.getInput(3).readText().day3a() shouldBe 532445
    }
    test("inputFileB") {
        Framework.getInput(3).readText().day3b() shouldBe 79842967
    }
}

class Day3Test : FunSpec(spec)

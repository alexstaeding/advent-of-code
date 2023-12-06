package aoc

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe

private val spec: FunSpec.() -> Unit = {
    test("inputFileExampleA") {
        Framework.getInput(5, useExample = true).readLines().day5a() shouldBe 35
    }
    test("inputFileExampleB") {
        Framework.getInput(5, useExample = true).readLines().day5b() shouldBe 46
    }
    test("inputFileA") {
        Framework.getInput(5).getReader().readLines().day5a() shouldBe 836040384
    }
    test("inputFileB") {
        Framework.getInput(5).getReader().readLines().day5b() shouldBe 10834440
    }
}

class Day5Test : FunSpec(spec)

package aoc

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe

private val spec: FunSpec.() -> Unit = {
    test("inputFileExampleA") {
        Framework.getInput(8, "a", useExample = true).getReader().day8a() shouldBe 6UL
    }
    test("inputFileExampleB") {
        Framework.getInput(8, "b", useExample = true).getReader().day8b() shouldBe 6UL
    }
    test("inputFileA") {
        Framework.getInput(8).getReader().day8a() shouldBe 17873UL
    }
    test("inputFileB") {
        Framework.getInput(8).getReader().day8b() shouldBe 15746133679061UL
    }
}

class Day8Test : FunSpec(spec)

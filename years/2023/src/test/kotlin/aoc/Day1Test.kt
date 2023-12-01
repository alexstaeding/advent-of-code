package aoc

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe

private val spec: FunSpec.() -> Unit = {
    test("inputFileExampleA") {
        Framework.getInput(1, "a", useExample = true).lineSequence().day1a() shouldBe 142
    }
    test("inputFileExampleB") {
        Framework.getInput(1, "b", useExample = true).lineSequence().day1b() shouldBe 281
    }
    test("inputFileA") {
        Framework.getInput(1).lineSequence().day1a() shouldBe 54916
    }
    test("inputFileB") {
        Framework.getInput(1).lineSequence().day1b() shouldBe 54728
    }
    test("inputFileBAlt") {
        Framework.getInput(1).lineSequence().day1bAlt() shouldBe 54728
    }
}

class Day1Test : FunSpec(spec)

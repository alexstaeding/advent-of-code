package aoc

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe

private val spec: FunSpec.() -> Unit = {
    test("inputFileExampleA") {
        Framework.getInput(2, useExample = true).lineSequence().day2a() shouldBe 2
    }
    test("inputFileA") {
        Framework.getInput(2, useExample = false).lineSequence().day2a() shouldBe 2
    }
}

class Day2Test : FunSpec(spec)

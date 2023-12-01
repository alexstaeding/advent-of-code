package aoc

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe

private val spec: FunSpec.() -> Unit = {
    test("inputFileA") {
        Framework.getInput(8).readText().day8a() shouldBe 1835
    }
    test("inputFileB") {
        Framework.getInput(8).readText().day8b() shouldBe 263670
    }
}

class Day8Test : FunSpec(spec)

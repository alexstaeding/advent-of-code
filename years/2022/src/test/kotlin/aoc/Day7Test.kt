package aoc

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe

private val spec: FunSpec.() -> Unit = {
    test("inputFileA") {
        Framework.getInput(7).readText().day7a() shouldBe 1644735
    }
    test("inputFileB") {
        Framework.getInput(7).readText().day7b() shouldBe 1300850
    }
}

class Day7Test : FunSpec(spec)

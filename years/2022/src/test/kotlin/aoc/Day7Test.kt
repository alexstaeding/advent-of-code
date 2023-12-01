package aoc

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe

private val spec: FunSpec.() -> Unit = {
    test("inputFileA") {
        getInput(7).day7a() shouldBe 1644735
    }
    test("inputFileB") {
        getInput(7).day7b() shouldBe 1300850
    }
}

class Day7Test : FunSpec(spec)

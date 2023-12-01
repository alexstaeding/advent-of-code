package aoc

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe

private val spec: FunSpec.() -> Unit = {
    test("inputFileA") {
        getInput(6).day6a() shouldBe 1275
    }
    test("inputFileB") {
        getInput(6).day6b() shouldBe 3605
    }
}

class Day6Test : FunSpec(spec)

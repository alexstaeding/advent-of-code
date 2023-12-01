package aoc

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe

private val spec: FunSpec.() -> Unit = {
    test("inputFileA") {
        Framework.getInput(6).readText().day6a() shouldBe 1275
    }
    test("inputFileB") {
        Framework.getInput(6).readText().day6b() shouldBe 3605
    }
}

class Day6Test : FunSpec(spec)

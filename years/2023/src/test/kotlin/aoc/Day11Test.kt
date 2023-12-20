package aoc

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe

private val spec: FunSpec.() -> Unit = {
    test("inputFileExampleA") {
        Framework.getInput(11, useExample = true).readLines().day11a() shouldBe 374L
    }
    test("inputFileExampleB") {
        Framework.getInput(11, useExample = true).readLines().day11b() shouldBe 82000210L
    }
    test("inputFileA") {
        Framework.getInput(11).readLines().day11a() shouldBe 9550717L
    }
    test("inputFileB") {
        Framework.getInput(11).readLines().day11b() shouldBe 648458253817L
    }
}

class Day11Test : FunSpec(spec)

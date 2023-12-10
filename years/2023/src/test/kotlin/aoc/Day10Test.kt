package aoc

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe

private val spec: FunSpec.() -> Unit = {
    test("inputFileExampleA") {
        Framework.getInput(10, "a", useExample = true).readLines().day10a() shouldBe 4
    }
    test("inputFileExampleB") {
        Framework.getInput(10, "b", useExample = true).readLines().day10b() shouldBe 4
    }
    test("inputFileExampleC") {
        Framework.getInput(10, "c", useExample = true).readLines().day10b() shouldBe 8
    }
    test("inputFileExampleD") {
        Framework.getInput(10, "d", useExample = true).readLines().day10b() shouldBe 10
    }
    test("inputFileA") {
        Framework.getInput(10).readLines().day10a() shouldBe 6599
    }
    test("inputFileB") {
        Framework.getInput(10).readLines().day10b() shouldBe 477
    }
}

class Day10Test : FunSpec(spec)

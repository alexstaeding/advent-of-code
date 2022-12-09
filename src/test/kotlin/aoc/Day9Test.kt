package aoc

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe

class Day9Test : FunSpec({
    test("moveUp1") {
        "U 1".day9a() shouldBe 1
    }
    test("moveUp2") {
        "U 2".day9a() shouldBe 2
    }
    test("moveUp10") {
        "U 10".day9a() shouldBe 10
    }
    test("moveRight1") {
        "R 1".day9a() shouldBe 1
    }
    test("moveRight2") {
        "R 2".day9a() shouldBe 2
    }
    test("moveRight10") {
        "R 10".day9a() shouldBe 10
    }
    test("moveDown1") {
        "D 1".day9a() shouldBe 1
    }
    test("moveDown2") {
        "D 2".day9a() shouldBe 2
    }
    test("moveDown10") {
        "D 10".day9a() shouldBe 10
    }
    test("moveLeft1") {
        "L 1".day9a() shouldBe 1
    }
    test("moveLeft2") {
        "L 2".day9a() shouldBe 2
    }
    test("moveLeft10") {
        "L 10".day9a() shouldBe 10
    }
})

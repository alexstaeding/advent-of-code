package aoc

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe

private val spec: FunSpec.() -> Unit = {
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
    test("touching") {
        Pos9(0, 0).isTouching(Pos9(0, 1)) shouldBe true
        Pos9(0, 0).isTouching(Pos9(1, 0)) shouldBe true
        Pos9(0, 0).isTouching(Pos9(1, 1)) shouldBe true
        Pos9(0, 0).isTouching(Pos9(1, -1)) shouldBe true
        Pos9(0, 0).isTouching(Pos9(-1, 1)) shouldBe true
        Pos9(0, 0).isTouching(Pos9(-1, -1)) shouldBe true
        Pos9(0, 0).isTouching(Pos9(2, 2)) shouldBe false
        Pos9(0, 0).isTouching(Pos9(2, 1)) shouldBe false
    }
    test("basicExampleA") {
        """
            R 4
            U 4
            L 3
            D 1
            R 4
            D 1
            L 5
            R 2
        """.trimIndent().day9a() shouldBe 13
    }
    test("inputFileA") {
        getInput(9).day9a() shouldBe 6181
    }
    test("basicExampleB") {
        """
            R 4
            U 4
            L 3
            D 1
            R 4
            D 1
            L 5
            R 2
        """.trimIndent().day9b() shouldBe 1
    }
    test("largeExampleB") {
        """
            R 5
            U 8
            L 8
            D 3
            R 17
            D 10
            L 25
            U 20
        """.trimIndent().day9b() shouldBe 36
    }
    test("inputFileB") {
        getInput(9).day9b() shouldBe 2386
    }
}

class Day9Test : FunSpec(spec)

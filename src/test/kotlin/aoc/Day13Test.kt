package aoc

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe

class Day13Test : FunSpec({
    test("singleLine1") {
        "[1,1,3,1,1]".parseBrackets() shouldBe
            SubList(listOf(Value(1), Value(1), Value(3), Value(1), Value(1)))
    }
    test("singleLine2") {
        "[[1],[2,3,4]]".parseBrackets() shouldBe
            SubList(listOf(SubList(listOf(Value(1))), SubList(listOf(Value(2), Value(3), Value(4)))))
    }
    test("singleLine3") {
        "[[1],4]".parseBrackets() shouldBe
            SubList(listOf(SubList(listOf(Value(1))), Value(4)))
    }
    test("singleLineEmptySimple") {
        "[]".parseBrackets() shouldBe
            SubList(listOf())
    }
    test("singleLineEmptyComplex") {
        "[[[]]]".parseBrackets() shouldBe
            SubList(listOf(SubList(listOf(SubList(listOf())))))
    }
    test("singleLineComplex") {
        "[1,[2,[3,[4,[5,6,7]]]],8,9]".parseBrackets() shouldBe
            SubList(
                listOf(
                    Value(1),
                    SubList(
                        listOf(
                            Value(2),
                            SubList(
                                listOf(
                                    Value(3), SubList(
                                        listOf(
                                            Value(4), SubList(
                                                listOf(
                                                    Value(5), Value(6), Value(7)
                                                )
                                            )
                                        )
                                    )
                                )
                            )
                        )
                    ),
                    Value(8),
                    Value(9),
                )
            )
    }
})

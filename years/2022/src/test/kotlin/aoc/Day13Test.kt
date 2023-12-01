package aoc

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.comparables.shouldBeGreaterThan
import io.kotest.matchers.comparables.shouldBeLessThan
import io.kotest.matchers.shouldBe

private val spec: FunSpec.() -> Unit = {
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
    test("singleLineMultipleDigits") {
        "[123,1,5]".parseBrackets() shouldBe
            SubList(listOf(Value(123), Value(1), Value(5)))
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
                                    Value(3),
                                    SubList(
                                        listOf(
                                            Value(4),
                                            SubList(
                                                listOf(
                                                    Value(5),
                                                    Value(6),
                                                    Value(7),
                                                ),
                                            ),
                                        ),
                                    ),
                                ),
                            ),
                        ),
                    ),
                    Value(8),
                    Value(9),
                ),
            )
    }
    test("compareBasic1") {
        "[1,1,3,1,1]".parseBrackets() shouldBeLessThan "[1,1,5,1,1]".parseBrackets()
    }
    test("compareBasic2") {
        "[[1],[2,3,4]]".parseBrackets() shouldBeLessThan "[[1],4]".parseBrackets()
    }
    test("compareBasic3") {
        "[9]".parseBrackets() shouldBeGreaterThan "[[8,7,6]]".parseBrackets()
    }
    test("compareBasic4") {
        "[[4,4],4,4]".parseBrackets() shouldBeLessThan "[[4,4],4,4,4]".parseBrackets()
    }
    test("compareBasic5") {
        "[7,7,7,7]".parseBrackets() shouldBeGreaterThan "[7,7,7]".parseBrackets()
    }
    test("compareBasic6") {
        "[]".parseBrackets() shouldBeLessThan "[3]".parseBrackets()
    }
    test("compareBasic7") {
        "[[[]]]".parseBrackets() shouldBeGreaterThan "[[]]".parseBrackets()
    }
    test("compareBasic8") {
        "[1,[2,[3,[4,[5,6,7]]]],8,9]".parseBrackets() shouldBeGreaterThan "[1,[2,[3,[4,[5,6,0]]]],8,9]".parseBrackets()
    }
    test("advanced1") {
        "[[9,2],[],[10,[6,1],5,4]]".parseBrackets() shouldBeLessThan
            "[[[9,[],3],[6],1],[[1],[[9,1],0,7,10],2,0]]".parseBrackets()
    }
    test("advanced2") {
        "[[[[9,0,4,8,4],[10,6],[9,9],[1,2]],2,2],[10,[[10,9,5,4,5]],[[10,7,1],[]],[[2,1,6],[2,6],0],10],[0,1,[2,4,[1,0,10],[4,1,1,8,5],2],10]]".parseBrackets() shouldBeGreaterThan
            "[[[6,6],8,6],[3,[0,10],9,9],[6,3,7,2]]".parseBrackets()
    }
    test("advanced3") {
        "[[],[[7,[5],3],1],[6,[],9,[7,[9,5,4,3,4]]]]".parseBrackets() shouldBeLessThan
            "[[2,[9,[3,1,6],[],1,[7,4,7,4,0]],7,4],[[[5,0]],[[1,2],[2,10],[]],[5,[],[8,0,0,1,6]],[[7,0],[0,2,7,9]]]]".parseBrackets()
    }
    test("advanced4") {
        "[[10,[[10,2],2],3,2],[5,7,[2,[9,4],0,[4,7],0]],[[6,6,10,6],[[7,5],[1,2,9,9,8],4,[9,7]],6],[8,[],8,8,[[4,6,7,7]]],[5,[7],[3,8],[0]]]".parseBrackets() shouldBeGreaterThan
            "[[[6,1,[]]],[8,[[6,10,0,4],2,10,[0,3,10,4],9],7,7]]".parseBrackets()
    }
    test("advanced5") {
        "[[10,4,[3,10,[7,6,10,4,8],3,[7]],4],[5]]".parseBrackets() shouldBeGreaterThan
            "[[4,[]],[[[8,2,4,6,10]],2,[7,[3],[]]],[10,10,2,[2],4],[],[[2,9,[10,2,8]],4]]".parseBrackets()
    }
    test("inputFileA") {
        getInput(13).day13a() shouldBe 5659
    }
    test("inputFileB") {
        getInput(13).day13b() shouldBe 22110
    }
}

class Day13Test : FunSpec(spec)

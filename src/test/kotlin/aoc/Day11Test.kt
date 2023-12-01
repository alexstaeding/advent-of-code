package aoc

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe

class Day11Test : FunSpec({
    test("parseOne") {
        """
            Monkey 0:
              Starting items: 92, 73, 86, 83, 65, 51, 55, 93
              Operation: new = old * 5
              Test: divisible by 11
                If true: throw to monkey 3
                If false: throw to monkey 4
        """.trimIndent().parseMonkey() shouldBe Monkey(
            0,
            ArrayDeque(listOf(92, 73, 86, 83, 65, 51, 55, 93)),
            { it * 5 },
            { it % 11 == 0L },
            3,
            4
        )
    }
    test("basicExampleA") {
        """
            Monkey 0:
              Starting items: 79, 98
              Operation: new = old * 19
              Test: divisible by 23
                If true: throw to monkey 2
                If false: throw to monkey 3

            Monkey 1:
              Starting items: 54, 65, 75, 74
              Operation: new = old + 6
              Test: divisible by 19
                If true: throw to monkey 2
                If false: throw to monkey 0

            Monkey 2:
              Starting items: 79, 60, 97
              Operation: new = old * old
              Test: divisible by 13
                If true: throw to monkey 1
                If false: throw to monkey 3

            Monkey 3:
              Starting items: 74
              Operation: new = old + 3
              Test: divisible by 17
                If true: throw to monkey 0
                If false: throw to monkey 1
        """.trimIndent().day11a() shouldBe 10605
    }
// Monke bad
//    test("inputFileA") {
//        getInput(11).day11a() shouldBe 0
//    }
})

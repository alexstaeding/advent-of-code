package aoc

val reg = """\s*Monkey (?<id>[0-9]+):
\s*Starting items: (?<items>.+)
\s*Operation: (?<op>.+)
\s*Test: (?<test>.+)
\s*If true: throw to monkey (?<ifTrue>[0-9]+)
\s*If false: throw to monkey (?<ifFalse>[0-9]+)\s*""".toRegex()

fun String.day11a(): Int {
    val monkeys = split("\n\n").map { it.parseMonkey() }
    for (i in 0 until 20) {
        println("Round $i")
        for (monkey in monkeys) {
            println(monkey)
            monkey.items.forEach { item ->
                monkey.inspected++
                val inspected = monkey.operation(item).floorDiv(3)
                val throwMonkey = if (monkey.test(inspected)) {
                    monkeys.find { it.id == monkey.throwToIfTrue }
                } else {
                    monkeys.find { it.id == monkey.throwToIfFalse }
                }
                checkNotNull(throwMonkey)
                throwMonkey.items.add(inspected)
            }
            monkey.items.clear()
        }
    }
    println("final")
    return monkeys.asSequence().sortedByDescending { it.inspected }.take(2)
        .onEach { println(it) }
        .zipWithNext { a, b -> a.inspected * b.inspected }.sum()
}

fun String.parseMonkey(): Monkey {
    val match = reg.matchEntire(this) ?: error("no match for $this")
    val id = match.groups["id"]!!.value.toInt()
    val items = match.groups["items"]!!.value.split(", ").mapTo(ArrayDeque()) { it.toInt() }
    val op = match.groups["op"]!!.value.parseOperation()
    val test = match.groups["test"]!!.value.parseTest()
    val ifTrue = match.groups["ifTrue"]!!.value.toInt()
    val ifFalse = match.groups["ifFalse"]!!.value.toInt()
    return Monkey(id, items, op, test, ifTrue, ifFalse)
}

fun String.parseOperation(): (Int) -> Int {
    val fullOp = substringAfter("new = ")
    val (leftId, opId, rightId) = fullOp.split(" ")
    val left: (Int) -> Int = runCatching { leftId.toInt() }.getOrNull()?.let { t -> { t } } ?: { it }
    val right: (Int) -> Int = runCatching { rightId.toInt() }.getOrNull()?.let { t -> { t } } ?: { it }
    val op: (Int, Int) -> Int = when (opId) {
        "*" -> { a, b -> a * b }
        "+" -> { a, b -> a + b }
        else -> error("unknown op $opId")
    }
    return { op(left(it), right(it)) }
}

fun String.parseTest(): (Int) -> Boolean {
    val divBy = substringAfter("divisible by ").toInt()
    return { it % divBy == 0 }
}

class Monkey(
    val id: Int,
    val items: ArrayDeque<Int>,
    val operation: (Int) -> Int,
    val test: (Int) -> Boolean,
    val throwToIfTrue: Int,
    val throwToIfFalse: Int,
) {
    var inspected = 0
    override fun toString(): String {
        return "Monkey(id=$id, items=$items, inspected=$inspected)"
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Monkey

        if (id != other.id) return false
        if (items != other.items) return false
        if (throwToIfTrue != other.throwToIfTrue) return false
        if (throwToIfFalse != other.throwToIfFalse) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id
        result = 31 * result + items.hashCode()
        result = 31 * result + throwToIfTrue
        result = 31 * result + throwToIfFalse
        return result
    }
}

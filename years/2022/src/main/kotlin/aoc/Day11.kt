package aoc

val reg = """\s*Monkey (?<id>[0-9]+):
\s*Starting items: (?<items>.+)
\s*Operation: (?<op>.+)
\s*Test: (?<test>.+)
\s*If true: throw to monkey (?<ifTrue>[0-9]+)
\s*If false: throw to monkey (?<ifFalse>[0-9]+)\s*""".toRegex()

fun String.day11a(): Long {
    val monkeys = split("\n\n").map { it.parseMonkey() }
    monkeys.forEach { println(it) }
    for (i in 0 until 20) {
        println("Round $i")
        for (monkey in monkeys) {
            println(monkey)
            monkey.items.forEach { item ->
                val inspected = monkey.operation(item) / 3
                monkey.inspected++
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
    return monkeys.asSequence().sortedByDescending { it.inspected }
        .onEach { println(it) }
        .toList()
        .take(2)
        .zipWithNext { a, b -> a.inspected * b.inspected }.sum().toLong()
}

fun String.parseMonkey(): Monkey {
    val match = reg.matchEntire(this) ?: error("no match for $this")
    val id = match.groups["id"]!!.value.toLong()
    val items = match.groups["items"]!!.value.split(", ").mapTo(ArrayDeque()) { it.toLong() }
    val op = match.groups["op"]!!.value.parseOperation()
    val test = match.groups["test"]!!.value.parseTest()
    val ifTrue = match.groups["ifTrue"]!!.value.toLong()
    val ifFalse = match.groups["ifFalse"]!!.value.toLong()
    return Monkey(id, items, op, test, ifTrue, ifFalse)
}

fun String.parseOperation(): (Long) -> Long {
    val fullOp = substringAfter("new = ")
    val (leftId, opId, rightId) = fullOp.split(" ")
    val left: (Long) -> Long = runCatching { leftId.toLong() }.getOrNull()?.let { t -> { t } } ?: { it }
    val right: (Long) -> Long = runCatching { rightId.toLong() }.getOrNull()?.let { t -> { t } } ?: { it }
    val op: (Long, Long) -> Long = when (opId) {
        "*" -> { a, b -> a * b }
        "+" -> { a, b -> a + b }
        else -> error("unknown op $opId")
    }
    return { op(left(it), right(it)) }
}

fun String.parseTest(): (Long) -> Boolean {
    val divBy = substringAfter("divisible by ").toLong()
    return { it % divBy == 0L }
}

class Monkey(
    val id: Long,
    val items: ArrayDeque<Long>,
    val operation: (Long) -> Long,
    val test: (Long) -> Boolean,
    val throwToIfTrue: Long,
    val throwToIfFalse: Long,
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
        return result.toInt()
    }
}

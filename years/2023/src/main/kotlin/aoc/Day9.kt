package aoc

fun main() {
    Framework.getInput(9, useExample = false).lineSequence().day9a().let { println(it) }
}

fun Sequence<String>.day9a(): Long = sumOf { s ->
    val firstLine = Regex("(-?\\d+)").findAll(s).map { it.value.toLong() }
        .toMutableList()

    val lines = mutableListOf(firstLine)

    while (!lines.last().all { it == 0L }) {
        lines += lines.last().windowed(2, 1) { (l, r) -> r - l }
            .toMutableList()
    }

    var lastDiff = 0L
    for ((i, line) in lines.reversed().withIndex()) {
        line += line.last() + lastDiff
        lastDiff = line.last()
    }

    lines.first().last()
}

package aoc

fun main() {
    Framework.getInput(9, useExample = false).lineSequence().day9b().let { println(it) }
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
    for (line in lines.reversed()) {
        line += line.last() + lastDiff
        lastDiff = line.last()
    }

    lines.first().last()
}

fun Sequence<String>.day9b(): Long = sumOf { s ->
    val firstLine = Regex("(-?\\d+)").findAll(s).map { it.value.toLong() }
        .toMutableList()

    val lines = mutableListOf(firstLine)

    while (!lines.last().all { it == 0L }) {
        lines += lines.last().windowed(2, 1) { (l, r) -> r - l }
            .toMutableList()
    }

    var lastDiff = 0L
    for (line in lines.reversed()) {
        line.add(0, line.first() - lastDiff)
        lastDiff = line.first()
    }

    lines.first().first()
}

package aoc

private fun String.calculateLines(): MutableList<MutableList<Long>> {
    val lines = mutableListOf(Regex("(-?\\d+)").findAll(this).map { it.value.toLong() }.toMutableList())
    while (!lines.last().all { it == 0L }) {
        lines += lines.last().windowed(2, 1) { (l, r) -> r - l }
            .toMutableList()
    }
    return lines
}

fun Sequence<String>.day9a(): Long = sumOf { s ->
    val lines = s.calculateLines()
    var lastDiff = 0L
    for (line in lines.reversed()) {
        line += line.last() + lastDiff
        lastDiff = line.last()
    }

    lines.first().last()
}

fun Sequence<String>.day9b(): Long = sumOf { s ->
    val lines = s.calculateLines()
    var lastDiff = 0L
    for (line in lines.reversed()) {
        line.add(0, line.first() - lastDiff)
        lastDiff = line.first()
    }

    lines.first().first()
}

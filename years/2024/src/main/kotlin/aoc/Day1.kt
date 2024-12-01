package aoc

import kotlin.math.abs

private val numPairRegex = """(\d+)\s+(\d+)""".toRegex()

private fun Sequence<String>.parseInput(): List<Pair<Int, Int>> =
    toList().map {
        (numPairRegex.matchEntire(it)
            ?: throw IllegalArgumentException("Invalid input format: $it"))
            .groupValues
    }.map { (_, a, b) -> a.toInt() to b.toInt() }

fun Sequence<String>.day1a(): Int {
    val all = parseInput()

    val left = all.map { it.first }.sorted()
    val right = all.map { it.second }.sorted()

    return left.zip(right).sumOf { (l, r) ->
        abs(l - r)
    }
}

fun Sequence<String>.day1b(): Int {
    val all = parseInput()

    val left = all.map { it.first }
    val right = all.map { it.second }.groupingBy { it }.eachCount()

    return left.sumOf {
        it * (right[it] ?: 0)
    }
}

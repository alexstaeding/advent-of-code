package aoc

import kotlin.math.abs

private val numPairRegex = """(\d+)\s+(\d+)""".toRegex()

fun Sequence<String>.day1a(): Int {
    val all = toList().map {
        (numPairRegex.matchEntire(it)
            ?: throw IllegalArgumentException("Invalid input format: $it"))
            .groupValues
    }.map { (_, a, b) -> a.toInt() to b.toInt() }

    val left = all.map { it.first }.sorted()
    val right = all.map { it.second }.sorted()

    return left.zip(right).sumOf { (l, r) ->
        abs(l - r)
    }
}

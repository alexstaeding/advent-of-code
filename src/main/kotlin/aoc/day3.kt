package aoc

fun main() = println(getInput(3).day3())

fun String.day3(): Int {
    return splitToSequence("\n").map { s ->
        val (left, right) = s.withIndex()
            .partition { it.index < s.length / 2 }
        left.map { it.value }
            .intersect(right.map { it.value }.toSet())
            .sumOf { c -> c.toScore() }
    }.sum()
}

fun Char.toScore() = (1 + code - 'a'.code).takeIf { it > 0 } ?: (27 + code - 'A'.code)

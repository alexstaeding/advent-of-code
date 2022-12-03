package aoc

fun String.day3a(): Int {
    return splitToSequence("\n").map { s ->
        val (left, right) = s.withIndex()
            .partition { it.index < s.length / 2 }
        left.map { it.value }
            .intersect(right.map { it.value }.toSet())
            .sumOf { c -> c.toScore() }
    }.sum()
}

fun String.day3b(): Int {
    return splitToSequence("\n").windowed(size = 3, step = 3) { (a, b, c) ->
        a.toSet().intersect(b.toSet()).intersect(c.toSet()).single().toScore()
    }.sum()
}

fun Char.toScore() = (1 + code - 'a'.code).takeIf { it > 0 } ?: (27 + code - 'A'.code)

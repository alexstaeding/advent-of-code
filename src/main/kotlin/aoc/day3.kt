package aoc

fun String.day3a(): Int = splitToSequence("\n").map { line ->
    val (left, right) = line.withIndex().partition { it.index < line.length / 2 }
    left.map { it.value }
        .intersect(right.map { it.value }.toSet())
        .sumOf { it.toScore() }
}.sum()

fun String.day3b(): Int = splitToSequence("\n")
    .windowed(3, 3) { (a, b, c) -> a.toSet().intersect(b.toSet()).intersect(c.toSet()).single().toScore() }
    .sum()

fun Char.toScore() = (1 + code - 'a'.code).takeIf { it > 0 } ?: (27 + code - 'A'.code)

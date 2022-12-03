package aoc

fun main() = println(getInput(3).day3())

fun String.day3(): Int {
    return splitToSequence("\n").map { s ->
        println("test: $s")
        val (left, right) = s.withIndex()
            .partition { it.index < s.length / 2 }
        left.map { it.value }
            .intersect(right.map { it.value }.toSet())
            .sumOf { c -> c.toScore() }
            .also { println("Sum: $it") }
    }.sum()
}

fun Char.toScore() = (1 + code - 'a'.code).takeIf { it > 0 } ?: (27 + code - 'A'.code)

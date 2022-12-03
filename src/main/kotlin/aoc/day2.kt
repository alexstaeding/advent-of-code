package aoc

fun main() = println(getInput(2).day2())

fun String.day2(): Int = splitToSequence("\n")
    .map { it[0].code - 'A'.code to it[2].code - 'X'.code }
    .sumOf { (a, b) -> (3 + (a + b - 1)) % 3 + 1 + b * 3 }

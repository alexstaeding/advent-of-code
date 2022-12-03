package aoc

fun String.day2(): Int = splitToSequence("\n")
    .map { it[0].code - 65 to it[2].code - 88 }
    .sumOf { (a, b) -> (2 + a + b) % 3 + 1 + b * 3 }

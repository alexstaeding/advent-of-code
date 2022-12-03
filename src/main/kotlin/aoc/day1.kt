package aoc

fun String.day1(): Sequence<Int> = splitToSequence("\n\n")
    .map { group -> group.split("\n").sumOf { it.toInt() } }
    .sortedDescending()

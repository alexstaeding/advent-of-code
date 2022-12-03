package aoc

fun main() = println(getInput(1).day1().take(3).toList())

fun String.day1(): Sequence<Int> = splitToSequence("\n\n")
    .map { group -> group.split("\n").sumOf { it.toInt() } }
    .sortedDescending()

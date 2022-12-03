package aoc

fun main() = println(getInput(3).day3())

fun String.day3(): Int {
    return withIndex().partition { it.index < length / 2 }.let { (left, right) ->
        left.map { it.value }.intersect(right.map { it.value }.toSet()).sumOf { it.code - ('a'.code - 1) }
    }
}

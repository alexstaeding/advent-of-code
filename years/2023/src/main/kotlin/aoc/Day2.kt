package aoc

import kotlin.math.max

val state = mutableMapOf(
    "red" to 12,
    "green" to 13,
    "blue" to 14,
)

fun String.day2a(): Int {
    return split("\n")
        .asSequence()
        .map { it.substringAfter(": ") }
        .map { it.split("; ") }
        .mapIndexed { index, strings -> index + 1 to strings.map { it.split(", ").map { x -> x.split(" ").zipWithNext().single() } } }
        .toList()
        .sumOf { (index, subGame) ->
            if (subGame.all { take ->
                    val stateCopy = state.toMutableMap()
                    for ((num, color) in take) {
                        stateCopy.computeIfPresent(color) { _, v -> v - num.toInt() }
                    }
                    stateCopy.all { (_, v) -> v >= 0 }
                }) {
                index
            } else 0
        }
}

fun String.day2b(): Int {
    return split("\n")
        .asSequence()
        .map { it.substringAfter(": ") }
        .map { it.split("; ") }
        .map { strings -> strings.map { it.split(", ").map { x -> x.split(" ").zipWithNext().single() } } }
        .sumOf { subGame ->
            val maxMap = mutableMapOf(
                "red" to 0,
                "green" to 0,
                "blue" to 0,
            )
            for (take in subGame) {
                for ((num, color) in take) {
                    maxMap.computeIfPresent(color) { _, v -> max(v, num.toInt()) }
                }
            }
            maxMap.values.reduce { a, v -> a * v }
        }
}

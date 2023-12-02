package aoc

import kotlin.math.max

val state = mutableMapOf(
    "red" to 12,
    "green" to 13,
    "blue" to 14,
)

fun String.day2a(): Int = splitToSequence("\n")
    .map { it.substringAfter(": ").split("; ") }
    .mapIndexed { index, strings -> index + 1 to strings.map { it.split(", ").map { x -> x.split(" ").zipWithNext().single() } } }
    .toList()
    .sumOf { (index, subGame) ->
        index.takeIf {
            subGame.all { takes ->
                val stateCopy = state.toMutableMap()
                for ((num, color) in takes) {
                    stateCopy.computeIfPresent(color) { _, v -> v - num.toInt() }
                }
                stateCopy.all { (_, v) -> v >= 0 }
            }
        } ?: 0
    }

fun String.day2b(): Int = splitToSequence("\n")
    .map { it.substringAfter(": ").split(", ", "; ") }
    .map { strings -> strings.map { it.split(" ").zipWithNext().single() } }
    .sumOf { takes ->
        val maxMap = mutableMapOf(
            "red" to 0,
            "green" to 0,
            "blue" to 0,
        )
        for ((num, color) in takes) {
            maxMap.computeIfPresent(color) { _, v -> max(v, num.toInt()) }
        }
        maxMap.values.reduce { a, v -> a * v }
    }

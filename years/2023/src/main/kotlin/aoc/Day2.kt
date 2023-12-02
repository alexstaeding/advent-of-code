package aoc

import kotlin.math.max

val state = mutableMapOf(
    "red" to 12,
    "green" to 13,
    "blue" to 14,
)

fun Sequence<String>.day2a(): Int = map { it.substringAfter(": ").split("; ") }
    .mapIndexed { index, strings -> index + 1 to strings.map { it.split(", ").map { x -> x.split(" ").zipWithNext().single() } } }
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

fun Sequence<String>.day2b(): Int = map { line -> line.substringAfter(": ").split(", ", "; ").map { it.split(" ").zipWithNext().single() } }
    .sumOf { takes ->
        val maxMap = state.mapValuesTo(mutableMapOf()) { 0 }
        for ((num, color) in takes) {
            maxMap.computeIfPresent(color) { _, v -> max(v, num.toInt()) }
        }
        maxMap.values.reduce { a, v -> a * v }
    }

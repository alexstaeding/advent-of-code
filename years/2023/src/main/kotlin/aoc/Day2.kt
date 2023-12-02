package aoc

import kotlin.math.max

val initialState = mutableMapOf("red" to 12, "green" to 13, "blue" to 14)

fun Sequence<String>.day2a(): Int = withIndex().sumOf { (index, line) ->
    (index + 1).takeIf { _ ->
        line.split(": ")[1].split("; ").map { it.split(", ").map { x -> x.split(" ") } }.all { takes ->
            val state = initialState.toMutableMap()
            takes.forEach { (num, color) -> state.computeIfPresent(color) { _, v -> v - num.toInt() } }
            state.values.all { it >= 0 }
        }
    } ?: 0
}

fun Sequence<String>.day2b(): Int = map { line -> line.substringAfter(": ").split(", ", "; ").map { it.split(" ") } }
    .sumOf { takes ->
        val minMap = initialState.mapValuesTo(mutableMapOf()) { 0 }
        takes.forEach { (num, color) -> minMap.computeIfPresent(color) { _, v -> max(v, num.toInt()) } }
        minMap.values.reduce { a, v -> a * v }
    }

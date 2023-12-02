package aoc

import kotlin.math.max

private val initialState = mutableMapOf("red" to 12, "green" to 13, "blue" to 14)

fun Sequence<String>.day2a(): Int = withIndex().filter { (_, line) ->
    line.split(": ")[1].split("; ").map { it.split(", ").map { x -> x.split(" ") } }.all { takes ->
        val state = initialState.toMutableMap()
        takes.forEach { (num, color) -> state.computeIfPresent(color) { _, v -> v - num.toInt() } }
        state.values.all { it >= 0 }
    }
}.sumOf { it.index + 1 }

fun Sequence<String>.day2b(): Int = sumOf { line ->
    val minMap = initialState.mapValuesTo(mutableMapOf()) { 0 }
    line.split(": ")[1].split("; ", ", ").map { it.split(" ") }
        .forEach { (num, color) -> minMap.computeIfPresent(color) { _, v -> max(v, num.toInt()) } }
    minMap.values.reduce { a, v -> a * v }
}

package aoc

import kotlin.math.max

val state = mutableMapOf("red" to 12, "green" to 13, "blue" to 14)

fun Sequence<String>.day2a(): Int = map { it.substringAfter(": ").split("; ") }
    .mapIndexed { index, strings -> index + 1 to strings.map { it.split(", ").map { x -> x.split(" ").zipWithNext().single() } } }
    .sumOf { (index, subGame) ->
        index.takeIf { _ ->
            subGame.all { takes ->
                state.toMutableMap().let {
                    takes.forEach { (num, color) -> it.computeIfPresent(color) { _, v -> v - num.toInt() } }
                    it.all { (_, v) -> v >= 0 }
                }
            }
        } ?: 0
    }

fun Sequence<String>.day2b(): Int = map { line -> line.substringAfter(": ").split(", ", "; ").map { it.split(" ").zipWithNext().single() } }
    .sumOf { takes ->
        state.mapValuesTo(mutableMapOf()) { 0 }.let {
            takes.forEach { (num, color) -> it.computeIfPresent(color) { _, v -> max(v, num.toInt()) } }
            it.values.reduce { a, v -> a * v }
        }
    }

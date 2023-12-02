package aoc

import kotlin.math.max
import kotlin.math.min

fun main() {
//    println("Result: " + Framework.getInput(2, "a", useExample = true).readText().day2a())
    println("Result: " + Framework.getInput(2).readText().day2a())
}

val state = mutableMapOf(
    "red" to 12,
    "green" to 13,
    "blue" to 14,
)

fun String.day2a(): Int {
    var possible = 0
    var power = 0
    split("\n")
        .asSequence()
        .map { it.substringAfter(": ") }
        .map { it.split("; ") }
        .mapIndexed { index, strings -> index + 1 to strings.map { it.split(", ").map { x -> x.split(" ").zipWithNext().single() } } }
        .toList()
        .forEach { (index, subGame: List<List<Pair<String, String>>>) ->
            val stateCopy = state.toMutableMap()
            println(stateCopy)
            var f = true
            val maxMap = mutableMapOf(
                "red" to 0,
                "green" to 0,
                "blue" to 0,
            )
            for (take in subGame) {
                for ((num, color) in take) {
                    stateCopy.computeIfPresent(color) { _, v ->
                        maxMap[color] = max(maxMap[color]!!, num.toInt())
                        v - num.toInt()
                    }
                }
            }
            power += maxMap.asSequence().fold(1) { acc, (_, v) -> acc * max(0, v) }
        }
    return power
}

package aoc

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
    split("\n")
        .asSequence()
        .map { it.substringAfter(": ") }
        .map { it.split(", ", "; ") }
        .mapIndexed { index, strings -> index + 1 to strings.map { it.split(" ").zipWithNext().single() } }
        .toList()
        .forEach { (index, games) ->
            val stateCopy = state.toMutableMap()
            println("Computing game $index")
            println(stateCopy)
            games.forEach { (num, color) ->
                stateCopy.computeIfPresent(color) { _, v -> v - num.toInt() }
            }
            println(stateCopy)
            if (stateCopy.all { (_, v) -> v >= 0 }) {
                println("Possible: $index")
                possible += index
            }
            // reset
        }
    return possible
}

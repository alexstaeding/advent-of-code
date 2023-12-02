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
        .map { it.split("; ") }
        .mapIndexed { index, strings -> index + 1 to strings.map { it.split(", ").map { x -> x.split(" ").zipWithNext().single() } } }
        .toList()
        .forEach { (index, subGame: List<List<Pair<String, String>>>) ->
            var stateCopy = state.toMutableMap()
            println(stateCopy)
            var f = true
            for (take in subGame) {
                for ((num, color) in take) {
                    stateCopy.computeIfPresent(color) { _, v -> v - num.toInt() }
                }
                if (!stateCopy.all { (_, v) -> v >= 0 }) {
                    f = false
                    break
                }
                stateCopy = state.toMutableMap()
            }
            if (f) {
                possible += index
            }
            println(stateCopy)

            // reset
        }
    return possible
}

package aoc

fun main() {
    val points = getInput(2)
        .splitToSequence("\n")
        .map { it.toGame() }
        .sumOf { it.calculatePlayerPoints() }
    println(points)
}

// model

data class Game(val opponent: Int, val outcome: Char)

// game logic

fun convert(n: Int) = (3 + n) % 3

fun Game.calculatePlayerPoints(): Int = opponent.pickHand(outcome) + 1 + (outcome.code - 'X'.code) * 3

/**
 * X = lose
 * Y = draw
 * Z = win
 */
fun Int.pickHand(outcome: Char): Int = when (outcome) {
    'X' -> convert(this - 1)
    'Y' -> this
    'Z' -> convert(this + 1)
    else -> error("Invalid outcome: $outcome")
}

// parsing

fun String.toGame() = Game(this[0].code - 'A'.code, this[2])

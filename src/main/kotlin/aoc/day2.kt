package aoc

fun main() {
    val points = getInput(2)
        .splitToSequence("\n")
        .map { it.toGame() }
        .sumOf { it.calculatePlayerPoints() }
    println(points)
}

// model

data class Game(val opponent: Hand, val outcome: Char)
enum class Hand {
    ROCK, PAPER, SCISSORS;

    companion object
}

// game logic

operator fun Hand.Companion.get(n: Int) = Hand.values()[(3 + n) % 3]

fun Game.calculatePlayerPoints(): Int = opponent.pickHand(outcome).ordinal + 1 + (outcome.code - 'X'.code) * 3

/**
 * X = lose
 * Y = draw
 * Z = win
 */
fun Hand.pickHand(outcome: Char): Hand = when (outcome) {
    'X' -> Hand[ordinal - 1]
    'Y' -> this
    'Z' -> Hand[ordinal + 1]
    else -> error("Invalid outcome: $outcome")
}

// parsing

fun String.toGame() = Game(this[0].toHand(), this[2])

fun Char.toHand(): Hand = when (this) {
    'A' -> Hand.ROCK
    'B' -> Hand.PAPER
    'C' -> Hand.SCISSORS
    else -> throw IllegalArgumentException("Invalid hand: $this")
}


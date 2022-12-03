package aoc

fun main() {
    val points = getInput(2)
        .splitToSequence("\n")
        .map { it.toGame() }
        .sumOf { it.calculatePlayerPoints() }
    println(points)
}

// model

data class Game(val opponent: Hand, val outcome: Outcome)
enum class Hand {
    ROCK, PAPER, SCISSORS;

    companion object
}

enum class Outcome { LOSE, DRAW, WIN }

// game logic

operator fun Hand.Companion.get(n: Int) = Hand.values()[(3 + n) % 3]

fun Game.calculatePlayerPoints(): Int = opponent.pickHand(outcome).ordinal + 1 + outcome.ordinal * 3

/**
 * X = lose
 * Y = draw
 * Z = win
 */
fun Hand.pickHand(outcome: Outcome): Hand = when (outcome) {
    Outcome.LOSE -> Hand[ordinal - 1]
    Outcome.DRAW -> this
    Outcome.WIN -> Hand[ordinal + 1]
    else -> error("Invalid outcome: $outcome")
}

// parsing

fun String.toGame() = Game(this[0].toHand(), this[2].toOutcome())

fun Char.toHand(): Hand = when (this) {
    'A' -> Hand.ROCK
    'B' -> Hand.PAPER
    'C' -> Hand.SCISSORS
    else -> throw IllegalArgumentException("Invalid hand: $this")
}

fun Char.toOutcome(): Outcome = when (this) {
    'X' -> Outcome.LOSE
    'Y' -> Outcome.DRAW
    'Z' -> Outcome.WIN
    else -> throw IllegalArgumentException("Invalid outcome: $this")
}


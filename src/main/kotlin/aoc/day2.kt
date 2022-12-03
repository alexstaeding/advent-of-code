package aoc

fun main() {
    val points = getInput(2)
        .splitToSequence("\n")
        .map { it.toGame() }
        .sumOf { it.calculatePlayerPoints() }
    println(points)
}

// model

data class Game(val opponent: Hand, val player: Hand)
enum class Hand { ROCK, PAPER, SCISSORS }

// game logic

fun Game.calculatePlayerPoints(): Int = when {
    opponent.beats(player) -> 0
    player.beats(opponent) -> 6
    else -> 3
} + player.ordinal + 1

fun Hand.beats(other: Hand): Boolean = when (this) {
    Hand.ROCK -> other == Hand.SCISSORS
    Hand.PAPER -> other == Hand.ROCK
    Hand.SCISSORS -> other == Hand.PAPER
}

// parsing

fun String.toGame(): Game {
    val (opponent, player) = split(" ")
    return Game(opponent.toHand(), player.toHand())
}

fun String.toHand(): Hand = when (this) {
    "A", "X" -> Hand.ROCK
    "B", "Y" -> Hand.PAPER
    "C", "Z" -> Hand.SCISSORS
    else -> throw IllegalArgumentException("Invalid hand: $this")
}


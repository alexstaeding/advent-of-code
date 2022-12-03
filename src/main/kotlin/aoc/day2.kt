package aoc

fun main() {
    val points = getInput(2)
        .splitToSequence("\n")
        .map { it.toGame() }
        .sumOf { it.calculatePlayerPoints() }
    println(points)
}

// model

data class Game(val opponent: Int, val outcome: Int)

// game logic

fun convert(n: Int) = (3 + n) % 3

fun Game.calculatePlayerPoints(): Int = convert(opponent + outcome - 1) + 1 + outcome * 3

// parsing

fun String.toGame() = Game(this[0].code - 'A'.code, this[2].code - 'X'.code)

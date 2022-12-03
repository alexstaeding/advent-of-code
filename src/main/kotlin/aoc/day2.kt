package aoc

fun main() {
    val points = getInput(2)
        .splitToSequence("\n")
        .map { it.toGame() }
        .sumOf { it.calculatePlayerPoints() }
    println(points)
}

data class Game(val opponent: Int, val outcome: Int)
fun Game.calculatePlayerPoints(): Int = (3 + (opponent + outcome - 1)) % 3 + 1 + outcome * 3
fun String.toGame() = Game(this[0].code - 'A'.code, this[2].code - 'X'.code)

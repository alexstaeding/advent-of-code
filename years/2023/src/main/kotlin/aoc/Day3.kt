package aoc

fun main() = Framework.getInput(3, useExample = false).readText().day3a().let { println(it) }

typealias Grid = Array<CharArray>

private data class Pos(val y: Int, val x: Int)

private operator fun Pos.plus(other: Pos) = Pos(y + other.y, x + other.x)

private fun Grid.checkSurround(point: Pos): Boolean {
    (-1..1).forEach { y ->
        (-1..1).forEach { x ->
            if (y != 0 || x != 0) {
                val pos = point + Pos(x, y)
                if (getOrNull(pos.y)?.getOrNull(pos.x)?.let { it != '.' && !it.isDigit() } == true) return true
            }
        }
    }
    return false
}

fun String.day3a(): Int {
    val grid = lines().map { it.toCharArray() }.toTypedArray()

    return grid.withIndex().sumOf { (y, row) ->

        var total = 0
        var lastNum = 0
        var numHasStar = false
        for ((x, c) in row.withIndex()) {
            if (!c.isDigit()) {
                if (numHasStar) {
                    println("Adding number $lastNum at $x, $y")
                    total += lastNum
                }
                numHasStar = false
            }
            if (c.isDigit()) {
                val pos = Pos(y, x)
                if (y == 4) {
                    println("Checking $pos: $lastNum = ${lastNum * 10 + c.digitToInt()}")
                }
                lastNum = lastNum * 10 + c.digitToInt()
                if (grid.checkSurround(pos)) {
//                    println("Found symbol near $x, $y ($c)")
                    numHasStar = true
                }
            } else {
                lastNum = 0
            }
        }
        if (numHasStar) {
            total += lastNum
        }
        total
    }
}

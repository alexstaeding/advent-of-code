package aoc

fun main() = Framework.getInput(3, useExample = false).readText().day3b().let { println(it) }

typealias Grid = Array<CharArray>

private data class Pos(val y: Int, val x: Int)

private operator fun Pos.plus(other: Pos) = Pos(y + other.y, x + other.x)

private data class GearCandidate(val num: Int, val symbolPos: Set<Pos>)

private fun Grid.checkSurround(point: Pos): Set<Pos> {
    val symbols = mutableSetOf<Pos>()
    (-1..1).forEach { y ->
        (-1..1).forEach { x ->
            if (y != 0 || x != 0) {
                val pos = point + Pos(x, y)
                if (getOrNull(pos.y)?.getOrNull(pos.x)?.let { it != '.' && !it.isDigit() } == true) symbols += pos
            }
        }
    }
    return symbols
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
                    total += lastNum
                }
                numHasStar = false
            }
            if (c.isDigit()) {
                val pos = Pos(y, x)
                lastNum = lastNum * 10 + c.digitToInt()
                if (grid.checkSurround(pos).isNotEmpty()) {
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

fun String.day3b(): Int {
    val grid = lines().map { it.toCharArray() }.toTypedArray()

    val allGearSymbols = mutableSetOf<Pos>()
    val candidates = mutableListOf<GearCandidate>()

    grid.withIndex().forEach { (y, row) ->
        var lastNum = 0
        val lastSymbolPos = mutableSetOf<Pos>()
        for ((x, c) in row.withIndex()) {
            if (!c.isDigit()) {
                if (lastSymbolPos.isNotEmpty()) {
                    candidates.add(GearCandidate(lastNum, lastSymbolPos.toSet()))
                }
                allGearSymbols += lastSymbolPos
                lastSymbolPos.clear()
            }
            if (c.isDigit()) {
                val pos = Pos(y, x)
                lastNum = lastNum * 10 + c.digitToInt()
                lastSymbolPos += grid.checkSurround(pos)
            } else {
                lastNum = 0
                allGearSymbols += lastSymbolPos
                lastSymbolPos.clear()
            }
        }
        if (lastSymbolPos.isNotEmpty()) {
            candidates.add(GearCandidate(lastNum, lastSymbolPos))
        }
        allGearSymbols += lastSymbolPos
    }

    return allGearSymbols.sumOf { gearSymbol ->
        val twoCandidates = candidates.filter { candidate -> gearSymbol in candidate.symbolPos }
        if (twoCandidates.size == 2) {
            val (a, b) = twoCandidates
            a.num * b.num
        } else if (twoCandidates.size > 2) {
            error("WTF $twoCandidates")
        } else 0
    }
}

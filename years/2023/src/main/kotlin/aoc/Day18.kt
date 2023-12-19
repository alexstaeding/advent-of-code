package aoc

private class Grid18 {
    private val grid = mutableListOf(mutableListOf<Char>())

    val width get() = grid[0].size
    val height get() = grid.size

    operator fun get(pos: Pos18) = grid[pos.y][pos.x]

    operator fun set(pos: Pos18, value: Char) {
        if (pos.y >= grid.size) {
            repeat(pos.y - grid.size + 1) { grid.add(MutableList(width) { '.' }) }
        }
        if (pos.x >= grid[pos.y].size) {
            repeat(pos.x - grid[pos.y].size + 1) { grid.forEach { it.add('.') } }
        }

        grid[pos.y][pos.x] = value
    }

    fun print() {
        grid.forEach { println(it.joinToString("")) }
    }

    fun asSequence(): Sequence<Pair<Pos18, Char>> = sequence {
        grid.forEachIndexed { y, row ->
            row.forEachIndexed { x, c ->
                yield(Pos18(y, x) to c)
            }
        }
    }
}

private data class Pos18(val y: Int, val x: Int) {
    override fun toString(): String {
        return "($y, $x)"
    }
}

private fun Grid18.isDefinitelyEnclosed(pos: Pos18): Boolean {
    val leftCount = (0..<pos.x)
        .map { Pos18(pos.y, it) }
        .count { this[it] == '#' }

    val rightCount = (pos.x + 1..<width)
        .map { Pos18(pos.y, it) }
        .count { this[it] == '#' }

    val upCount = (0..<pos.y)
        .map { Pos18(it, pos.x) }
        .count { this[it] == '#' }

    val downCount = (pos.y + 1..<height)
        .map { Pos18(it, pos.x) }
        .count { this[it] == '#' }

    return leftCount % 2 == 1 && rightCount % 2 == 1 && upCount % 2 == 1 && downCount % 2 == 1
}

private operator fun Grid18.contains(pos: Pos18) = pos.y in 0 until height && pos.x in 0 until width

private val deepFillGrid: DeepRecursiveFunction<Pair<Grid18, Pos18>, Unit> =
    DeepRecursiveFunction { (grid, pos) ->
        if (pos !in grid || grid[pos] != '.') return@DeepRecursiveFunction
        grid[pos] = '#'
        callRecursive(grid to Pos18(pos.y + 1, pos.x))
        callRecursive(grid to Pos18(pos.y - 1, pos.x))
        callRecursive(grid to Pos18(pos.y, pos.x + 1))
        callRecursive(grid to Pos18(pos.y, pos.x - 1))
    }

private enum class Dir18 { R, D, L, U }

fun main() {
    Framework.getInput(18, useExample = true).readText().day18b().let { println(it) }
}

private fun String.makeGrid(parser: (String) -> Pair<Dir18, Int>): Grid18 {
    var current = Pos18(310, 130)
//    var current = Pos18(0, 0)
    val grid = Grid18()
    lines().forEach { line ->
        val (dir, count) = parser(line)
        repeat(count) {
            val next = when (dir) {
                Dir18.R -> Pos18(current.y, current.x + 1)
                Dir18.D -> Pos18(current.y + 1, current.x)
                Dir18.L -> Pos18(current.y, current.x - 1)
                Dir18.U -> Pos18(current.y - 1, current.x)
            }
            grid[next] = '#'
            current = next
        }
    }
    return grid
}

private fun Grid18.solveGrid(): Int {
    val inside = asSequence().map { it.first }.first { isDefinitelyEnclosed(it) }
    deepFillGrid(this to inside)
    return asSequence().map { it.second }.count { it == '#' }
}

fun String.day18a(): Int {
    return makeGrid { line ->
        val (dirString, countString) = Regex("([RDLU]) (\\d+).*").find(line)!!.destructured
        Dir18.valueOf(dirString) to countString.toInt()
    }.solveGrid()
}

fun String.day18b(): Int {
    return makeGrid { line ->
        val substr = line.substringAfterLast(' ')
        println("substr: $substr")
        val (num) = Regex("\\(#(.*)\\)")
            .find(substr)!!.destructured

        val distance = num.substring(0..<5).toInt(16)
        val dir = Dir18.entries[num[5].toString().toInt()]
        println("$distance $dir")
        dir to distance
    }.solveGrid()
}

package aoc

private typealias Grid13 = Array<CharArray>

private data class Pos13(val y: Int, val x: Int)

fun main() {
    Framework.getInput(13, useExample = false).readText().day13a().let { println(it) }
}

fun Grid13.reflectsAtVertical(index: Int): Boolean {
    var left = index
    var right = index + 1

    while (left >= 0 && right < this[0].size) {
        if (map { it[left] } != map { it[right] }) {
            return false
        }
        left--
        right++
    }

    return true
}

fun Grid13.reflectsAtHorizontal(index: Int): Boolean {
    var top = index
    var bottom = index + 1

    while (top >= 0 && bottom < this.size) {
        if (!this[top].contentEquals(this[bottom])) {
            return false
        }
        top--
        bottom++
    }

    return true
}

fun String.day13a(): Int {
    val grids: List<Grid13> = split("\n\n").map { g -> g.lines().map { it.toCharArray() }.toTypedArray() }

    return grids.sumOf { grid ->

        val vertical = (0..<grid[0].lastIndex)
            .firstOrNull { grid.reflectsAtVertical(it) }
            ?.let { it + 1 }

        val horizontal = (0..<grid.lastIndex)
            .firstOrNull { grid.reflectsAtHorizontal(it) }
            ?.let { it + 1 }

        vertical ?: (100 * horizontal!!)
    }
}

package aoc

private typealias Grid13 = Array<CharArray>

private data class Pos13(val y: Int, val x: Int)

fun main() {
    Framework.getInput(13, useExample = false).readText().day13b().let { println(it) }
}

private fun <T> List<T>.compareWithTolerance(other: List<T>, tolerance: Int): Pair<Boolean, Boolean> {
    var mismatched = 0

    for (i in indices) {
        if (this[i] != other[i]) {
            mismatched++
            if (mismatched > tolerance) {
                return false to false
            }
        }
    }
    return true to (mismatched == tolerance)
}

private fun Grid13.reflectsAtVertical(index: Int, fixSmudge: Boolean = false): Boolean {
    var left = index
    var right = index + 1
    var fixed = false
    while (left >= 0 && right < this[0].size) {
        val (match, smudged) = map { it[left] }.compareWithTolerance(map { it[right] }, if (fixSmudge && !fixed) 1 else 0)

        if (!match) {
            return false
        }

        if (smudged) {
            fixed = true
        }

        left--
        right++
    }

    return true
}

fun Grid13.reflectsAtHorizontal(index: Int, fixSmudge: Boolean = false): Boolean {
    var top = index
    var bottom = index + 1
    var fixed = false

    while (top >= 0 && bottom < this.size) {

        val (match, smudged) = this[top].toList().compareWithTolerance(this[bottom].toList(), if (fixSmudge && !fixed) 1 else 0)

        if (!match) {
            return false
        }

        if (smudged) {
            fixed = true
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

fun String.day13b(): Int {
    val grids: List<Grid13> = split("\n\n").map { g -> g.lines().map { it.toCharArray() }.toTypedArray() }

    return grids.sumOf { grid ->
        val vertical = (0..<grid[0].lastIndex)
            .filter { !grid.reflectsAtVertical(it) }
            .firstOrNull { grid.reflectsAtVertical(it, true) }
            ?.let { it + 1 }

        val horizontal = (0..<grid.lastIndex)
            .filter { !grid.reflectsAtHorizontal(it) }
            .firstOrNull { grid.reflectsAtHorizontal(it, true) }
            ?.let { it + 1 }

        vertical ?: (100 * horizontal!!)
    }
}

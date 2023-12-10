package aoc

private typealias Grid10 = Array<CharArray>

private data class Pos10(val y: Int, val x: Int) {
    override fun toString(): String = "($y, $x)"
}

private operator fun Pos10.plus(other: Pos10) = Pos10(y + other.y, x + other.x)
private operator fun Grid10.get(pos: Pos10) = this[pos.y][pos.x]
private operator fun Grid10.contains(pos: Pos10) = pos.y in indices && pos.x in this[pos.y].indices

private fun Grid10.findStart(): Pos10 {
    return withIndex().firstNotNullOf { (y, r) ->
        r.withIndex().firstNotNullOfOrNull { (x, c) -> x.takeIf { c == 'S' } }?.let { Pos10(y, it) }
    }
}

private fun Grid10.findNext(pos: Pos10): List<Pos10> {
    val grid = this
    val up: MutableList<Pos10>.() -> Unit = {
        (pos + Pos10(-1, 0)).takeIf { it in grid && grid[it] in setOf('|', '7', 'F') }
            ?.also { add(it) }
    }
    val down: MutableList<Pos10>.() -> Unit = {
        (pos + Pos10(1, 0)).takeIf { it in grid && grid[it] in setOf('|', 'L', 'J') }
            ?.also { add(it) }
    }
    val left: MutableList<Pos10>.() -> Unit = {
        (pos + Pos10(0, -1)).takeIf { it in grid && grid[it] in setOf('-', 'L', 'F') }
            ?.also { add(it) }
    }
    val right: MutableList<Pos10>.() -> Unit = {
        (pos + Pos10(0, 1)).takeIf { it in grid && grid[it] in setOf('-', 'J', '7') }
            ?.also { add(it) }
    }
    return when (this[pos]) {
        '|' -> buildList {
            up()
            down()
        }
        '-' -> buildList {
            left()
            right()
        }
        'L' -> buildList {
            up()
            right()
        }
        'J' -> buildList {
            up()
            left()
        }
        '7' -> buildList {
            down()
            left()
        }
        'F' -> buildList {
            down()
            right()
        }
        else -> buildList {
            up()
            down()
            left()
            right()
        }
    }
}

private fun Grid10.findPath(): Set<Pos10> {
    val start = findStart()

    val visited = mutableSetOf(start)

    val firstStep = findNext(start).first()
    val queue = mutableListOf(firstStep)
    while (queue.isNotEmpty()) {
        val pos = queue.removeFirst()
        if (pos in visited) continue
        visited += pos
        queue += findNext(pos)
    }
    return visited
}

fun List<String>.day10a(): Int = (map { it.toCharArray() }.toTypedArray().findPath().size + 1) / 2

private fun Grid10.isEnclosed(pos: Pos10, path: Set<Pos10>): Boolean {
    val leftCount = (0..<pos.x)
        .map { Pos10(pos.y, it) }
        .count { this[it] in setOf('|', 'L', 'J') && it in path }

    val rightCount = (pos.x + 1..this[pos.y].lastIndex)
        .map { Pos10(pos.y, it) }
        .count { this[it] in setOf('|', 'L', 'J') && it in path }

    return pos !in path && leftCount % 2 == 1 && rightCount % 2 == 1
}

fun List<String>.day10b(): Int {
    val grid = map { it.toCharArray() }.toTypedArray()
    val path = grid.findPath()
    return grid.withIndex().sumOf { (y, r) ->
        r.withIndex().count { (x, c) ->
            grid.isEnclosed(Pos10(y, x), path)
        }
    }
}

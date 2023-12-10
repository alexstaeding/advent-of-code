package aoc

private typealias Grid10 = Array<CharArray>

private data class Pos10(val y: Int, val x: Int) {
    override fun toString(): String = "($y, $x)"
}

private operator fun Pos10.plus(other: Pos10) = Pos10(y + other.y, x + other.x)
private operator fun Grid10.get(pos: Pos10) = this[pos.y][pos.x]

fun main() {
    Framework.getInput(10, useExample = false).readLines().day10a().let { println(it) }
}

private fun Grid10.findStart(): Pos10 {
    return withIndex().firstNotNullOf { (y, r) ->
        r.withIndex().firstNotNullOfOrNull { (x, c) -> x.takeIf { c == 'S' } }?.let { Pos10(y, it) }
    }
}

private fun Grid10.findNext(pos: Pos10): List<Pos10> {
    val grid = this
    val up: MutableList<Pos10>.() -> Unit = {
        (pos + Pos10(-1, 0)).takeIf { grid[it] in setOf('|', '7', 'F') }
            ?.also { add(it) }
    }
    val down: MutableList<Pos10>.() -> Unit = {
        (pos + Pos10(1, 0)).takeIf { grid[it] in setOf('|', 'L', 'J') }
            ?.also { add(it) }
    }
    val left: MutableList<Pos10>.() -> Unit = {
        (pos + Pos10(0, -1)).takeIf { grid[it] in setOf('-', 'L', 'F') }
            ?.also { add(it) }
    }
    val right: MutableList<Pos10>.() -> Unit = {
        (pos + Pos10(0, 1)).takeIf { grid[it] in setOf('-', 'J', '7') }
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

fun List<String>.day10a(): Int {
    val grid = map { it.toCharArray() }.toTypedArray()

    val start = grid.findStart()

    val visited = mutableSetOf(start)

    val firstStep = grid.findNext(start).first()
    val queue = mutableListOf(firstStep)
    while (queue.isNotEmpty()) {
        val pos = queue.removeFirst()
        if (pos in visited) continue
        visited += pos
        val next = grid.findNext(pos)
        println("Visiting $pos -> $next")
        queue += next
    }
    return (visited.size + 1) / 2
}

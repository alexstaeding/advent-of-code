package aoc

private typealias Grid16 = List<List<Char>>

private data class Pos16(val y: Int, val x: Int)

private operator fun Grid16.get(pos: Pos16) = this[pos.y][pos.x]
private operator fun Grid16.contains(pos: Pos16) = pos.y in indices && pos.x in this[pos.y].indices
private operator fun Pos16.plus(other: Pos16) = Pos16(y + other.y, x + other.x)

private enum class Dir16(val pos: Pos16) {
    UP(Pos16(-1, 0)),
    DOWN(Pos16(1, 0)),
    LEFT(Pos16(0, -1)),
    RIGHT(Pos16(0, 1)),
}

private data class Beam(val pos: Pos16, val dir: Dir16)

private fun Beam.redirect(dir: Dir16) = Beam(this.pos + dir.pos, dir)

fun main() {
    Framework.getInput(16, useExample = false).readText().day16b().let { println(it) }
}

private fun Grid16.doThing(start: Beam): Int {
    val queue = ArrayDeque<Beam>()
    queue.add(start)
    val visited = mutableSetOf<Beam>()

    while (queue.isNotEmpty()) {
        val current = queue.removeFirst()
        if (current.pos !in this || current in visited) {
            continue
        }

        visited.add(current)
        when (this[current.pos]) {
            '|' -> {
                when (current.dir) {
                    Dir16.UP, Dir16.DOWN -> queue.add(Beam(current.pos + current.dir.pos, current.dir))
                    Dir16.LEFT, Dir16.RIGHT -> {
                        queue.add(current.redirect(Dir16.UP))
                        queue.add(current.redirect(Dir16.DOWN))
                    }
                }
            }
            '-' -> {
                when (current.dir) {
                    Dir16.LEFT, Dir16.RIGHT -> queue.add(Beam(current.pos + current.dir.pos, current.dir))
                    Dir16.UP, Dir16.DOWN -> {
                        queue.add(current.redirect(Dir16.LEFT))
                        queue.add(current.redirect(Dir16.RIGHT))
                    }
                }
            }
            '/' -> {
                when (current.dir) {
                    Dir16.UP -> queue.add(current.redirect(Dir16.RIGHT))
                    Dir16.DOWN -> queue.add(current.redirect(Dir16.LEFT))
                    Dir16.LEFT -> queue.add(current.redirect(Dir16.DOWN))
                    Dir16.RIGHT -> queue.add(current.redirect(Dir16.UP))
                }
            }
            '\\' -> {
                when (current.dir) {
                    Dir16.DOWN -> queue.add(current.redirect(Dir16.RIGHT))
                    Dir16.UP -> queue.add(current.redirect(Dir16.LEFT))
                    Dir16.RIGHT -> queue.add(current.redirect(Dir16.DOWN))
                    Dir16.LEFT -> queue.add(current.redirect(Dir16.UP))
                }
            }
            else -> {
                queue.add(Beam(current.pos + current.dir.pos, current.dir))
            }
        }
    }
    return visited.mapTo(mutableSetOf()) { it.pos }.size
}

private fun String.day16a(): Int {
    val grid = lines().map { it.toList() }
    return grid.doThing(Beam(Pos16(0, 0), Dir16.RIGHT))
}

private fun String.day16b(): Int {
    val grid = lines().map { it.toList() }

    val top = List(grid[0].size) { Pos16(0, it) }.map { Beam(it, Dir16.DOWN) }
    val bottom = List(grid[0].size) { Pos16(grid.size - 1, it) }.map { Beam(it, Dir16.UP) }
    val left = List(grid.size) { Pos16(it, 0) }.map { Beam(it, Dir16.RIGHT) }
    val right = List(grid.size) { Pos16(it, grid[0].size - 1) }.map { Beam(it, Dir16.LEFT) }

    val all = top + bottom + left + right
    return all.maxOf { grid.doThing(it) }
}

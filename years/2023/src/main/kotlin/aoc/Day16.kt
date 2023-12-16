package aoc

private typealias Grid16 = List<List<Char>>

private data class Pos16(val y: Int, val x: Int)

private enum class Dir16(val pos: Pos16) {
    UP(Pos16(-1, 0)),
    DOWN(Pos16(1, 0)),
    LEFT(Pos16(0, -1)),
    RIGHT(Pos16(0, 1)),
}

private operator fun Pos16.plus(other: Pos16) = Pos16(y + other.y, x + other.x)

private data class BeamPos(val pos: Pos16, val dir: Dir16)

private val BeamPos.nextPos: BeamPos
    get() = BeamPos(pos + dir.pos, dir)

private operator fun Grid16.get(pos: Pos16) = this[pos.y][pos.x]
private operator fun Grid16.contains(pos: Pos16) = pos.y in indices && pos.x in this[pos.y].indices

fun main() {
    Framework.getInput(16, useExample = false).readText().day16a().let { println(it) }
}

private fun Grid16.println(visited: Set<Pos16>) {
    for ((y, row) in withIndex()) {
        for ((x, cell) in row.withIndex()) {
            if (Pos16(y, x) in visited) {
                print('#')
            } else {
                print(cell)
            }
        }
        println()
    }
}

fun String.day16a(): Int {
    val grid = lines().map { it.toList() }
    val queue = ArrayDeque<BeamPos>()
    queue.add(BeamPos(Pos16(0, 0), Dir16.RIGHT))
    val visited = mutableSetOf<Pos16>()

    while (queue.isNotEmpty()) {
        val current = queue.removeFirst()
        if (current.pos !in grid) {
            continue
        }
        println("Visited ${visited.size}")

        visited.add(current.pos)
        when (grid[current.pos]) {
            '|' -> {
                when (current.dir) {
                    Dir16.UP, Dir16.DOWN -> queue.add(BeamPos(current.pos + current.dir.pos, current.dir))
                    Dir16.LEFT, Dir16.RIGHT -> {
                        queue.add(BeamPos(current.pos + Dir16.UP.pos, Dir16.UP))
                        queue.add(BeamPos(current.pos + Dir16.DOWN.pos, Dir16.DOWN))
                    }
                }
            }
            '-' -> {
                when (current.dir) {
                    Dir16.LEFT, Dir16.RIGHT -> queue.add(BeamPos(current.pos + current.dir.pos, current.dir))
                    Dir16.UP, Dir16.DOWN -> {
                        queue.add(BeamPos(current.pos + Dir16.LEFT.pos, Dir16.LEFT))
                        queue.add(BeamPos(current.pos + Dir16.RIGHT.pos, Dir16.RIGHT))
                    }
                }
            }
            '/' -> {
                when (current.dir) {
                    Dir16.UP -> queue.add(BeamPos(current.pos + Dir16.RIGHT.pos, Dir16.RIGHT))
                    Dir16.DOWN -> queue.add(BeamPos(current.pos + Dir16.LEFT.pos, Dir16.LEFT))
                    Dir16.LEFT -> queue.add(BeamPos(current.pos + Dir16.DOWN.pos, Dir16.DOWN))
                    Dir16.RIGHT -> queue.add(BeamPos(current.pos + Dir16.UP.pos, Dir16.UP))
                }
            }
            '\\' -> {
                when (current.dir) {
                    Dir16.DOWN -> queue.add(BeamPos(current.pos + Dir16.RIGHT.pos, Dir16.RIGHT))
                    Dir16.UP -> queue.add(BeamPos(current.pos + Dir16.LEFT.pos, Dir16.LEFT))
                    Dir16.RIGHT -> queue.add(BeamPos(current.pos + Dir16.DOWN.pos, Dir16.DOWN))
                    Dir16.LEFT -> queue.add(BeamPos(current.pos + Dir16.UP.pos, Dir16.UP))
                }
            }
            else -> {
                queue.add(BeamPos(current.pos + current.dir.pos, current.dir))
            }
        }
    }
    return visited.size
}

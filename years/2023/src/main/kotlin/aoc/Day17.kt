package aoc

import org.fusesource.jansi.Ansi
import java.util.PriorityQueue
import kotlin.math.pow
import kotlin.math.sqrt

private typealias Grid17 = List<List<Int>>

private operator fun Grid17.get(pos: Pos17): Int = this[pos.y][pos.x]
private operator fun Grid17.contains(pos: Pos17) = pos.y in indices && pos.x in this[pos.y].indices

private data class Pos17(val y: Int, val x: Int) {
    override fun toString(): String = "($y, $x)"
}

private data class Node17(val current: Beam17, val previous: Node17?) {
    override fun toString(): String = "$current"
}

private data class Beam17(val pos: Pos17, val dir: Dir17) {
    override fun toString(): String = "$pos->$dir"
}

private operator fun Pos17.plus(other: Pos17) = Pos17(y + other.y, x + other.x)
private fun Pos17.directionTo(other: Pos17) = when {
    other.y < y -> Dir17.UP
    other.y > y -> Dir17.DOWN
    other.x < x -> Dir17.LEFT
    other.x > x -> Dir17.RIGHT
    else -> error("Cannot get direction to self")
}

private enum class Dir17(val pos: Pos17) {
    UP(Pos17(-1, 0)),
    RIGHT(Pos17(0, 1)),
    DOWN(Pos17(1, 0)),
    LEFT(Pos17(0, -1)),
}

private operator fun Dir17.plus(n: Int): Dir17 =
    Dir17.entries[(ordinal + n) % 4]

private operator fun Dir17.minus(n: Int): Dir17 =
    Dir17.entries[(ordinal - n + 4) % 4]

private fun Dir17.toChar(): Char = when (this) {
    Dir17.UP -> '^'
    Dir17.RIGHT -> '>'
    Dir17.DOWN -> 'v'
    Dir17.LEFT -> '<'
}

private fun Node17.countStraight(): Int {
    return generateSequence(seed = this) { it.previous }
        .takeWhile { it.current.dir == current.dir }
        .count()
}

private fun Node17.cost(grid: Grid17): Int {
    return generateSequence(seed = this) { it.previous }
        .takeWhile { it.previous != null }
        .sumOf { grid[it.current.pos] }
}

private fun Node17.asSequence(): Sequence<Node17> =
    generateSequence(seed = this) { it.previous }

private fun Beam17.redirect(dir: Dir17) = Beam17(this.pos + dir.pos, dir)
private fun Beam17.continueStraight() = redirect(dir)

private fun Pos17.distanceTo(other: Pos17): Double =
    sqrt(
        (other.y - y).toDouble().pow(2) +
            (other.x - x).toDouble().pow(2),
    )

fun main() {
    Framework.getInput(17, useExample = true).readText().day17a().let { println(it) }
}

private fun Grid17.findPath(start: Beam17, end: Pos17): Node17? {
    val comparator: Comparator<Node17> =
        Comparator.comparing<Node17, Int> { it.cost(this) }
            .then(Comparator.comparing { it.asSequence().count() })
            .then(Comparator.comparing { it.current.pos.distanceTo(end) })

    val queue = PriorityQueue(comparator)
    val visited = mutableSetOf<Beam17>()
    queue.add(Node17(start, null))
    val solutions = mutableListOf<Node17>()
    while (queue.isNotEmpty()) {
        val node = queue.remove()
        if (node.current in visited || (node.current.pos !in this)) {
            continue
        }

        visited.add(node.current)
        if (node.current.pos == end) {
            println("Found solution with penalty ${node.cost(this)}")
            solutions.add(node)
        }

        if (node.countStraight() < 3) {
            node.current.continueStraight().takeIf { it.pos in this }?.let {
                queue.add(Node17(it, node))
            }
        }

        node.current.redirect(node.current.dir - 1).takeIf { it.pos in this }?.let {
            queue.add(Node17(it, node))
        }
        node.current.redirect(node.current.dir + 1).takeIf { it.pos in this }?.let {
            queue.add(Node17(it, node))
        }
    }
    return solutions.minByOrNull { it.cost(this) }
}

private fun Grid17.printPath(path: Node17) {
    val grid = map { it.mapTo(mutableListOf()) { n -> n.digitToChar() } }
    val nodes = generateSequence(path) { it.previous }.toList()
    grid.withIndex().forEach { (y, row) ->
        row.withIndex().forEach { (x, cell) ->
            val pos = Pos17(y, x)
            val cellNode = nodes.firstOrNull { it.current.pos == pos }
            if (cellNode != null) {
                print(Ansi.ansi().fgBlue().a(cell))
//                print(Ansi.ansi().fgBlue().a(cellNode.current.dir.toChar()))
            } else {
                print(Ansi.ansi().fgDefault().a(cell))
            }
        }
        println()
    }
}

fun String.day17a(): Int {
    val grid = lines().map { l -> l.map { it.digitToInt() } }
    val start = Beam17(Pos17(0, 0), Dir17.RIGHT)
    val end = Pos17(grid.lastIndex, grid[0].lastIndex)

    val path = grid.findPath(start, end)
    checkNotNull(path) { "No path found" }

    grid.printPath(path)

    return path.cost(grid) - grid[start.pos]
}

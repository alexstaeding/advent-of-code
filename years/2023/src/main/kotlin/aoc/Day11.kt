package aoc

import java.util.PriorityQueue
import kotlin.math.absoluteValue

private typealias Grid11 = Array<CharArray>

private data class Pos11(val y: Int, val x: Int) {
    override fun toString(): String = "($y, $x)"
}

private operator fun Pos11.plus(other: Pos11) = Pos11(y + other.y, x + other.x)
private fun Pos11.distanceTo(other: Pos11) = (other.y - y).absoluteValue + (other.x - x).absoluteValue
private fun Pos11.neighbors(): Set<Pos11> {
    return setOf(
        Pos11(y - 1, x),
        Pos11(y + 1, x),
        Pos11(y, x - 1),
        Pos11(y, x + 1),
    )
}

private operator fun Grid11.get(pos: Pos11) = this[pos.y][pos.x]
private operator fun Grid11.contains(pos: Pos11) = pos.y in indices && pos.x in this[pos.y].indices

fun main() {
    Framework.getInput(11, useExample = false).readLines().day11a().let { println(it) }
}

private fun Grid11.findGalaxies(emptyRows: List<Int>, emptyCols: List<Int>): Set<Pos11> {
    return flatMapIndexedTo(mutableSetOf()) { y, r ->
        r.asSequence().mapIndexedNotNullTo(mutableSetOf()) { x, c ->
            x.takeIf { c == '#' }
                ?.let { _ -> Pos11(y + emptyRows.count { it < y }, x + emptyCols.count { it < x }) }
        }
    }
}

private fun findPath(a: Pos11, b: Pos11): List<Pos11> {
    data class Node(val pos: Pos11, val dist: Int, val prev: Node?)

    val visited = mutableSetOf<Pos11>()
    val queue = PriorityQueue<Node> { a, b -> a.dist.compareTo(b.dist) }
    queue.add(Node(a, a.distanceTo(b), null))
    while (queue.isNotEmpty()) {
        val current = queue.remove()
        if (current.pos in visited) {
            continue
        }
        if (current.pos == b) {
            val path = mutableListOf<Pos11>()
            var node = current
            while (node.prev != null) {
                path.add(node.pos)
                node = node.prev!!
            }
            return path.reversed()
        }
        visited.add(current.pos)
        current.pos.neighbors().forEach { neighbor ->
            queue.add(Node(neighbor, neighbor.distanceTo(b), current))
        }
    }
    return emptyList()
}

fun List<String>.day11a(): Int {
    val grid = map { it.toCharArray() }.toTypedArray()

    val emptyRows = grid.indices.filter { y -> grid[y].all { it == '.' } }
    val emptyCols = grid[0].indices.filter { x -> grid.all { it[x] == '.' } }
    val galaxies = grid.findGalaxies(emptyRows, emptyCols)

    val pairs = galaxies.flatMapTo(mutableSetOf()) { a ->
        galaxies.filter { b -> a != b }.map { b -> setOf(a, b) }
    }.map { it.toList().let { (a, b) -> a to b } }

    return pairs.sumOf { (a, b) -> findPath(a, b).size }
}

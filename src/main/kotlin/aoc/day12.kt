package aoc

import java.util.PriorityQueue
import kotlin.math.abs

fun main() {
    println(getInput(12).day12a())
}

fun String.day12a(): Int {
    val state = readState()
    val startPos = state.find(0)
    state.print()
    println("startPos = $startPos")
    val steps = state.findGoal()
    checkNotNull(steps) { "No solution" }
    println(steps.withIndex().joinToString("\n") { (i, step) -> "$i: $step" })
    return steps.size
}

var counter = 0


// A star search
private fun State12.findGoal(): List<Pos12>? {
    val queue = PriorityQueue<Node12> { a, b -> compareValuesBy(a, b) { it.pathSize + it.distance } }
    queue.add(this[start] ?: return null)
    while (queue.isNotEmpty()) {
        val node = queue.poll()
        println("node = $node")
        if (node.pos == goal) {
            return node.path
        }
        for (dir in Dir12.values()) {
            val newNode = this[node.pos.moveOne(dir)] ?: continue
            if (newNode.height > node.height + 1) continue
            if (newNode.pathSize > node.pathSize + 1) {
                newNode.path.clear()
                newNode.path.addAll(node.path)
                newNode.path.add(newNode.pos)
                queue.add(newNode)
            }
        }
    }
    return null
}

private data class Pos12(val y: Int, val x: Int) {
    override fun toString(): String = "($y, $x)"
}

private enum class Dir12 { N, E, S, W }

private data class State12(
    val heightmap: List<List<Node12>>,
    val start: Pos12,
    val goal: Pos12,
)

private data class Node12(
    val pos: Pos12,
    val height: Int,
    val distance: Int,
    val path: MutableList<Pos12> = mutableListOf(),
    val isStart: Boolean = false,
) {
    val pathSize: Int
        get() = if (path.isEmpty() && !isStart) Int.MAX_VALUE shr 1 else path.size
}

private fun State12.print() {
    heightmap.forEach { ints ->
        println(ints.joinToString("") { "($it)" })
    }
}

private operator fun State12.get(pos: Pos12): Node12? = heightmap.getOrNull(pos.y)?.getOrNull(pos.x)

private fun State12.find(height: Int): Pos12 {
    return heightmap.mapIndexedNotNull { y, nodes ->
        nodes.indexOfFirst { it.height == height }
            .takeIf { it >= 0 }
            ?.let { y to it }
    }.first().toPos()
}

private fun Pos12.moveOne(dir: Dir12) = when (dir) {
    Dir12.N -> copy(y = y - 1)
    Dir12.E -> copy(x = x + 1)
    Dir12.S -> copy(y = y + 1)
    Dir12.W -> copy(x = x - 1)
}

private fun Pair<Int, Int>.toPos() = Pos12(first, second)
private fun Pos12.distanceTo(other: Pos12): Int = abs(y - other.y) + abs(x - other.x)

private fun String.readState(): State12 {
    // read height map
    // S = 0
    // a = 1
    // z = 26
    // E = 27
    var startPos: Pos12? = null
    var endPos: Pos12? = null
    val heights = lines().mapIndexed { y, line ->
        line.mapIndexed { x, c ->
            when (c) {
                'S' -> {
                    startPos = Pos12(y, x)
                    0
                }
                'E' -> {
                    endPos = Pos12(y, x)
                    27
                }

                else -> 1 + c.code - 'a'.code
            }
        }
    }
    val start = checkNotNull(startPos) { "No start position" }
    val goal = checkNotNull(endPos) { "No end position" }
    val heightMap = heights.mapIndexed { y, line ->
        line.mapIndexed { x, h ->
            val pos = Pos12(y, x)
            Node12(pos, h, pos.distanceTo(goal), isStart = pos == start)
        }
    }
    return State12(heightMap, start, goal)
}

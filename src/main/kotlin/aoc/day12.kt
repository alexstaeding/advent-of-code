package aoc

import kotlin.math.abs

fun main() {
    println(getInput(12).day12a())
}

fun String.day12a(): Int {
    val state = readState()
    val startPos = state.find(0)
    val goalPos = state.find(-27)
    state.print()
    println("startPos = $startPos")
    val steps = state.findGoal(startPos, goalPos)
    checkNotNull(steps) { "No solution" }
    println(steps.withIndex().joinToString("\n") { (i, step) -> "$i: $step" })
    return steps.size
}

var counter = 0

private fun State12.findGoal(current: Pos12, goal: Pos12, steps: List<Pos12> = listOf()): List<Pos12>? {
    if (++counter % 1000000 == 0) {
        println("checking $steps :: $current")
    }
    if (steps.size > 30) {
//        println("Too many steps $current")
        return null
    }
    if (current == goal && this[steps.last()] == 26) {
        // goal
        return steps
    }
//    pos to (steps + current)
    val height = this[current]!!
    val calls = Dir12.values().asSequence()
        .map { dir -> current.moveOne(dir) }
        .filter { it !in steps }
        .mapNotNull { pos ->
            val delta = (this[pos] ?: return@mapNotNull null) - height
            if (delta > 1) return@mapNotNull null
            Triple(delta, pos, steps + current)
        }//.toList()
//    println("calls =\n${calls.joinToString("\n") { "${it.first}, ${it.third.last()}->${it.second}" }}")
//    val sortedCalls = calls
        .sortedWith(
            compareBy(
                { (_, pos, _) -> pos.distanceTo(goal) },
                { (delta, _, _) -> -delta },
            ),
        )
        .forEach { (_, pos, steps) ->
            findGoal(pos, goal, steps)?.let { return it }
        }

//    println("sortedCalls =\n${sortedCalls.joinToString("\n") { "${it.first}, ${it.third.last()}->${it.second}" }}")
    return null
}

private data class Move12(val dir: Dir12, val pos: Pos12)
private data class Pos12(val y: Int, val x: Int) {
    override fun toString(): String {
        return "($y, $x)"
    }
}

private enum class Dir12 { N, E, S, W }

private typealias State12 = List<List<Int>>

private fun State12.print() {
    forEach { ints ->
        println(ints.joinToString("") { "($it)" })
    }
}

private operator fun State12.get(pos: Pos12): Int? = getOrNull(pos.y)?.getOrNull(pos.x)

private fun State12.find(num: Int): Pos12 {
    return mapIndexedNotNull { y, ints ->
        ints.indexOf(num)
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
    // a = 0
    // z = 26
    return lines().map { line -> line.map { if (it == 'S') 0 else 1 + it.code - 'a'.code } }
}

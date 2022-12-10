package aoc

import kotlin.math.abs
import kotlin.math.absoluteValue

fun String.day9a(): Int = day9(1)
fun String.day9b(): Int = day9(10)

// simulate rope physics
// return number of positions the tail visited
private fun String.day9(tailCount: Int): Int {
    tailVisited.clear()
    tailVisited.add(Pos9(0, 0))
    lineSequence().map { it.split(" ") }.fold(State9(tailCount)) { state, (dir, count) ->
        state.moveHead(Dir9.valueOf(dir), count.toInt())
    }.also { println(it) }
    println("tail visited")
    println(tailVisited.joinToString("\n"))
    return tailVisited.size
}

// top left is 0,0
private data class Pos9(val y: Int, val x: Int) {
    override fun toString(): String = "($y, $x)"
}

private operator fun Pos9.minus(other: Pos9): Pos9 = Pos9(y - other.y, x - other.x)

// the rope only has two elements: head and tail
private data class State9(
    // the current position of the head of the rope
    val head: Pos9 = Pos9(0, 0),
    // the current position of the tail of the rope
    val tail: Link9 = Link9(Pos9(0, 0)),
) {
    constructor(tailCount: Int) : this(tail = initializeLink9(Pos9(0, 0), tailCount))
}

private fun State9.printState(dir: Dir9) {
    val topLeftBounds = Pos9(-15, -20)
    val bottomRightBounds = Pos9(5, 20)
    val width = bottomRightBounds.x - topLeftBounds.x
    repeat((width - 1) / 2) { print('=') }
    print(" $dir ")
    repeat((width - 1) / 2) { print('=') }
    println()
    val grid = Array(bottomRightBounds.y - topLeftBounds.y + 1) { Array(bottomRightBounds.x - topLeftBounds.x + 1) { '.' } }
    grid[-topLeftBounds.y][-topLeftBounds.x] = 'S'
    var link = tail
    var num = 1
    while (link.next != null) {
        grid[link.current.y - topLeftBounds.y][link.current.x - topLeftBounds.x] = "${num++}".single()
        link = link.next!!
    }
    grid[head.y - topLeftBounds.y][head.x - topLeftBounds.x] = 'H'
    grid.forEach { println(it.joinToString("")) }
}

private data class Link9(
    val current: Pos9,
    var next: Link9? = null,
)

private fun initializeLink9(pos: Pos9, tailCount: Int = 1): Link9 = when (tailCount) {
    1 -> Link9(pos)
    else -> Link9(pos, initializeLink9(pos, tailCount - 1))
}

// the rope can only move in four directions
private enum class Dir9 { U, L, D, R }

// use the axis with the greatest difference
private fun Pos9.dirTo(other: Pos9): Dir9 = when {
    abs(y - other.y) > abs(x - other.x) -> if (y < other.y) Dir9.D else Dir9.U
    else -> if (x < other.x) Dir9.R else Dir9.L
}

// the rope can only move one position at a time
private fun Pos9.moveOne(dir: Dir9): Pos9 = when (dir) {
    Dir9.U -> copy(y = y - 1)
    Dir9.L -> copy(x = x - 1)
    Dir9.D -> copy(y = y + 1)
    Dir9.R -> copy(x = x + 1)
}

private fun Pos9.isTouching(other: Pos9): Boolean = abs(x - other.x) <= 1 && abs(y - other.y) <= 1

private fun Pos9.isDiagonal(other: Pos9): Boolean = abs(x - other.x) == 1 && abs(y - other.y) == 1

private fun Link9.calculate(oldHead: Pos9, newHead: Pos9): Link9 {
    if (current.isTouching(newHead)) {
        return this
    }
    
    val newLink = Link9(oldHead)
    if (next == null) {
        // last element in the rope
        tailVisited.add(oldHead)
    }
    newLink.next = next?.calculate(current, oldHead)
    println("Calculating new link ${next?.current}->$current => ${newLink.next?.current}->${newLink.current} :: HEAD $oldHead->$newHead")
    return newLink
}

private val tailVisited = mutableSetOf<Pos9>()

private fun State9.moveHead(dir: Dir9, count: Int): State9 {
    printState(dir)
    return if (count < 2) {
        moveHead(dir)
    } else {
        val s = moveHead(dir)
        s.moveHead(dir, count - 1)
    }
}

private fun State9.moveHead(dir: Dir9): State9 = head.moveOne(dir).let { newHead ->
    // calculate new tail position
    val newTail = tail.calculate(head, newHead)
    // return new state
    State9(newHead, newTail)
}

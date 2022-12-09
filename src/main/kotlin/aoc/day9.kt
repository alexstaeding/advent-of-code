package aoc

import kotlin.math.abs

// simulate rope physics
// return number of positions the tail visited
fun String.day9a(): Int {
    return lineSequence().map { it.split(" ") }.fold(State9()) { state, (dir, count) ->
        state.moveHead(Dir9.valueOf(dir), count.toInt())
    }.tailVisited.size
}

// top left is 0,0
private data class Pos9(val y: Int, val x: Int)

// the rope only has two elements: head and tail
private data class State9(
    // the current position of the head of the rope
    val head: Pos9 = Pos9(0, 0),
    // the current position of the tail of the rope
    val tail: Pos9 = Pos9(0, 0),
    // the number of positions the tail has previously visited
    val tailVisited: Set<Pos9> = setOf(Pos9(0, 0)),
)

// the rope can only move in four directions
private enum class Dir9 { U, L, D, R }

// the rope can only move one position at a time
private fun Pos9.moveOne(dir: Dir9): Pos9 = when (dir) {
    Dir9.U -> copy(y = y - 1)
    Dir9.L -> copy(x = x - 1)
    Dir9.D -> copy(y = y + 1)
    Dir9.R -> copy(x = x + 1)
}

private fun Pos9.isTouching(other: Pos9): Boolean = abs(x - other.x) <= 1 && abs(y - other.y) <= 1

private fun State9.calculateNewTailPos(newHead: Pos9, newHeadDir: Dir9): Pos9 {
    if (newHead.isTouching(tail)) return tail
    return head
}

private fun State9.moveHead(dir: Dir9, count: Int): State9 {
    return if (count < 2) {
        moveHead(dir)
    } else {
        val s = moveHead(dir)
        s.moveHead(dir, count - 1)
    }
}

private fun State9.moveHead(dir: Dir9): State9 = head.moveOne(dir).let { newHead ->
    // calculate new tail position
    val newTail = calculateNewTailPos(newHead, dir)
    // return new state
    State9(newHead, newTail, tailVisited + newTail)
}

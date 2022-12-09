package aoc

import kotlin.math.abs

// simulate rope physics
// return number of positions the tail visited
fun String.day9a(): Int {
    return lineSequence().map { it.split(" ") }.fold(State9()) { state, (dir, count) ->
        println("State: $state, dir: $dir, count: $count")
        state.moveHead(Dir9.valueOf(dir), count.toInt())
    }.also { println("Final state: $it") }.tailVisited
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
    val tailVisited: Int = 1,
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

private fun State9.calculateNewTailPos(newHead: Pos9): Pos9 {
    // calculate relative position of head to tail
    val rel = Pos9(newHead.y - tail.y, newHead.x - tail.x)
    if (rel.y == 0) {
        if (abs(rel.x) < 2) {
            return tail
        }
        check(rel.x == -2 || rel.x == 2) { "invalid relative position: $rel" }
        return Pos9(tail.y, tail.x + rel.x / abs(rel.x))
    }
    if (rel.x == 0) {
        if (abs(rel.y) < 2) {
            return tail
        }
        check(rel.y == -2 || rel.y == 2) { "invalid relative position: $rel" }
        return Pos9(tail.y + rel.y / abs(rel.y), tail.x)
    }
    return tail
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
    val newTail = calculateNewTailPos(newHead)
    // calculate new tail visited
    val newTailVisited = if (newTail == tail) tailVisited else tailVisited + 1
    // return new state
    State9(newHead, newTail, newTailVisited)
}

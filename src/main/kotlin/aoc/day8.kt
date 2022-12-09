package aoc

fun String.day8a(): Int {
    val forest = split("\n").map { line -> line.map { it.digitToInt() } }
    return forest.indices.sumOf { y -> forest[y].indices.count { x -> forest.isVisible((Pos(y, x))) } }
}

fun String.day8b(): Int {
    val forest = split("\n").map { line -> line.map { it.digitToInt() } }
    return forest.indices.maxOf { y -> forest[y].indices.maxOf { x -> forest.getViewingDistance((Pos(y, x))) } }
}

typealias Forest = List<List<Int>>

data class Pos(val y: Int, val x: Int)
enum class Dir { N, E, S, W }

fun Pos.moveOne(dir: Dir): Pos = when (dir) {
    Dir.N -> copy(y = y - 1)
    Dir.E -> copy(x = x + 1)
    Dir.S -> copy(y = y + 1)
    Dir.W -> copy(x = x - 1)
}

operator fun Forest.get(pos: Pos) = getOrNull(pos.y)?.getOrNull(pos.x) ?: -1
operator fun Forest.contains(pos: Pos) = getOrNull(pos.y)?.getOrNull(pos.x) != null
fun Forest.isVisible(pos: Pos): Boolean = Dir.values().any { isVisible(this[pos], pos, it) }

fun Forest.isVisible(og: Int, pos: Pos, dir: Dir): Boolean {
    if (pos !in this) return true
    return og > this[pos.moveOne(dir)] && isVisible(og, pos.moveOne(dir), dir)
}

fun Forest.getViewingDistance(pos: Pos): Int = Dir.values().fold(1) { a, b -> a * getViewingDistance(this[pos], pos, b) }

fun Forest.getViewingDistance(og: Int, pos: Pos, dir: Dir): Int {
    if (pos !in this) return -1
    val newPos = pos.moveOne(dir)
    return when {
        og == this[newPos] -> 1
        og > this[newPos] -> 1 + getViewingDistance(og, pos.moveOne(dir), dir)
        else -> 1
    }
}

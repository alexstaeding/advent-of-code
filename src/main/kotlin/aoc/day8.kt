package aoc

fun String.day8a(): Int {
    val forest = split("\n").map { line -> line.map { it.digitToInt() } }
    return forest.indices.sumOf { y -> forest[y].indices.count { x -> forest.isVisible((Pos8(y, x))) } }
}

fun String.day8b(): Int {
    val forest = split("\n").map { line -> line.map { it.digitToInt() } }
    return forest.indices.maxOf { y -> forest[y].indices.maxOf { x -> forest.getViewingDistance((Pos8(y, x))) } }
}

private typealias Forest8 = List<List<Int>>

private data class Pos8(val y: Int, val x: Int)
private enum class Dir8 { N, E, S, W }

private fun Pos8.moveOne(dir: Dir8): Pos8 = when (dir) {
    Dir8.N -> copy(y = y - 1)
    Dir8.E -> copy(x = x + 1)
    Dir8.S -> copy(y = y + 1)
    Dir8.W -> copy(x = x - 1)
}

private operator fun Forest8.get(pos: Pos8) = getOrNull(pos.y)?.getOrNull(pos.x) ?: -1
private operator fun Forest8.contains(pos: Pos8) = getOrNull(pos.y)?.getOrNull(pos.x) != null
private fun Forest8.isVisible(pos: Pos8): Boolean = Dir8.values().any { isVisible(this[pos], pos, it) }

private fun Forest8.isVisible(og: Int, pos: Pos8, dir: Dir8): Boolean {
    if (pos !in this) return true
    return og > this[pos.moveOne(dir)] && isVisible(og, pos.moveOne(dir), dir)
}

private fun Forest8.getViewingDistance(pos: Pos8): Int = Dir8.values().fold(1) { a, b -> a * getViewingDistance(this[pos], pos, b) }

private fun Forest8.getViewingDistance(og: Int, pos: Pos8, dir: Dir8): Int {
    if (pos !in this) return -1
    val newPos = pos.moveOne(dir)
    return when {
        og == this[newPos] -> 1
        og > this[newPos] -> 1 + getViewingDistance(og, pos.moveOne(dir), dir)
        else -> 1
    }
}

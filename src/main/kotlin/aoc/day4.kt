package aoc

fun main() {
    println(getReader(4).readLines().sumOf { it.day4() })
}

fun String.day4(): Int {
    val (left, right) = split(",").map { it.toRange().toSet() }
    val intersection = left.intersect(right)
    if (intersection == left || intersection == right) return 1
    return 0
}

fun String.toRange(): IntRange {
    val (a, b) = split("-")
    return a.toInt()..b.toInt()
}

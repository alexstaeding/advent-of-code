package aoc

fun main() {
    println(getInput(4).day4b())
}

fun String.day4a(): Int = split("\n").sumOf {
    val (left, right) = it.split(",").map { it.toRange().toSet() }
    val intersection = left.intersect(right)
    val r: Int = if (intersection == left || intersection == right) 1 else 0
    r
}

fun String.day4b(): Int = split("\n").sumOf { line ->
    line.split(",")
        .map { it.toRange() }
        .zipWithNext { a, b -> a.intersect(b).isEmpty() }
        .count { !it }
}

fun String.toRange() = split("-").map { it.toInt() }.zipWithNext().single().let { (a, b) -> a..b }

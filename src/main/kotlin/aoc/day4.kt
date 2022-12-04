package aoc

fun main() {
    println(getInput(4).day4b())
}

fun String.day4a(): Int = split("\n").sumOf { line ->
    line.split(",")
        .map { it.toRange().toSet() }
        .zipWithNext { a, b -> a.intersect(b).let { it == a || it == b } }
        .count { it }
}

fun String.day4b(): Int = split("\n").sumOf { line ->
    line.split(",")
        .map { it.toRange() }
        .zipWithNext { a, b -> a.intersect(b).isEmpty() }
        .count { !it }
}

fun String.toRange() = split("-").map { it.toInt() }.zipWithNext().single().let { (a, b) -> a..b }

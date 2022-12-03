package aoc

fun main() {
    println(getLargest(getInput(1)).take(3).toList())
}

fun getLargest(text: String): Sequence<Int> =
    text.split("\n\n".toRegex()).asSequence()
        .map { group -> group.split("\n".toRegex()).sumOf { it.toInt() } }
        .sortedDescending()

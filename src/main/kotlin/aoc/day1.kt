package aoc

fun main() {
    val text = checkNotNull(ClassLoader.getSystemResourceAsStream("aoc/input1.txt")) { "input1.txt not found" }
        .reader().readText().trimEnd()
    println(getLargest(text).take(3).toList())
}

fun getLargest(text: String): Sequence<Int> =
    text.split("\n\n".toRegex()).asSequence()
        .map { group -> group.split("\n".toRegex()).sumOf { it.toInt() } }
        .sortedDescending()

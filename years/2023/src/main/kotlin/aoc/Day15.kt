package aoc

fun main() {
    Framework.getInput(15, useExample = false).readText().day15a().let { println(it) }
}

fun String.doHash(): Int {
    var current = 0
    for (c in this) {
        current += c.code
        current *= 17
        current %= 256
    }
    println("Hash of $this -> $current")
    return current
}

fun String.day15a(): Int = split(",").sumOf { it.doHash() }

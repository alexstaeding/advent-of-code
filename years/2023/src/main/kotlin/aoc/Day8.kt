package aoc

import java.io.BufferedReader

fun main() {
    Framework.getInput(8, "a", useExample = true).getReader().day8a().let { println(it) }
}

val regex = Regex("([A-Z]+) = \\(([A-Z]+), ([A-Z]+)\\)")

fun BufferedReader.day8a(): Int {
    val instructionLine = readLine().toList()
    val instructions = sequence {
        while (true) yieldAll(instructionLine)
    }.iterator()
    readLine()
    val map = readLines()
        .map { regex.findAll(it).single().destructured }
        .associate { (id, l, r) -> id to (l to r) }
    var current = "AAA"
    var i = 0
    while (current != "ZZZ") {
        i++
        current = map[current]!!.let { (l, r) ->
            if (instructions.next() == 'L') {
                l
            } else r
        }
    }
    return i
}

package aoc

import java.io.BufferedReader

fun main() {
    Framework.getInput(8, useExample = false).getReader().day8b().let { println(it) }
}

val regex = Regex("([1-9A-Z]+) = \\(([1-9A-Z]+), ([1-9A-Z]+)\\)")

private fun solve(
    start: String,
    map: Map<String, Pair<String, String>>,
    instructionLine: List<Char>,
    terminateWhen: (String) -> Boolean,
): ULong {
    val instructions = sequence {
        while (true) yieldAll(instructionLine)
    }.iterator()
    var current = start
    var i = 0UL
    while (!terminateWhen(current)) {
        i++
        current = map[current]!!.let { (l, r) ->
            if (instructions.next() == 'L') {
                l
            } else r
        }
    }
    return i
}

fun BufferedReader.day8a(): ULong {
    val instructionLine = readLine().toList()
    readLine()
    val map = readLines()
        .map { regex.findAll(it).single().destructured }
        .associate { (id, l, r) -> id to (l to r) }
    return solve("AAA", map, instructionLine) { it == "ZZZ" }
}

fun BufferedReader.day8b(): ULong {
    val instructionLine = readLine().toList()
    readLine()
    val map = readLines()
        .map { regex.findAll(it).single().destructured }
        .associate { (id, l, r) -> id to (l to r) }
    return map.keys.filter { it.endsWith('A') }.map { start ->
        solve(start, map, instructionLine) { it.endsWith('Z') }
    }.reduce { a, b -> lcm(a, b) }
}

private fun gcd(a: ULong, b: ULong): ULong = if (b == 0UL) a else gcd(b, a % b)
private fun lcm(a: ULong, b: ULong): ULong = a / gcd(a, b) * b

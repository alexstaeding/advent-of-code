package aoc

import java.io.BufferedReader

fun main() {
    Framework.getInput(6, useExample = false).getReader().day6b().let { println(it) }
}

fun BufferedReader.day6a(): Int {
    val (times, records) = List(2) { "(\\d+)".toRegex().findAll(readLine().split(":")[1]).map { it.value.toInt() } }
    return times.zip(records).map { (time, record) ->
        // for each speed
        (0..time)
            .map { t -> (time - t) * t }
            .count { it > record }
    }.reduce { a, b -> a * b }
}

fun BufferedReader.day6b(): Int {
    val (time, record) = List(2) { readLine().split(":")[1].replace("\\s+".toRegex(), "").toLong() }
    return (0..time)
        .map { t -> (time - t) * t }
        .count { it > record }
}

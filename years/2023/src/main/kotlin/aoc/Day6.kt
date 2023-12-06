package aoc

import java.io.BufferedReader

fun main() {
    Framework.getInput(6, useExample = false).getReader().day6b().let { println(it) }
}

fun BufferedReader.day6a(): Int {
    val times = "(\\d+)".toRegex().findAll(readLine().split(":")[1]).map { it.value.toInt() }
    val distances = "(\\d+)".toRegex().findAll(readLine().split(":")[1]).map { it.value.toInt() }
    return times.zip(distances).map { (time, distanceRecord) ->
        // for each speed
        (0..time)
            .map { t -> (time - t) * t }
            .count { it > distanceRecord }
    }.reduce { a, b -> a * b }
}

fun BufferedReader.day6b(): Int {
    val time = readLine().split(":")[1].replace("\\s+".toRegex(), "").toLong()
    val distanceRecord = readLine().split(":")[1].replace("\\s+".toRegex(), "").toLong()
    return (0..time)
        .map { t -> (time - t) * t }
        .count { it > distanceRecord }
}

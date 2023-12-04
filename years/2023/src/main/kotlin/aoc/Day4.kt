package aoc

import java.math.BigInteger

fun main() {
    Framework.getInput(4, useExample = false).lineSequence().day4a().let { println(it) }
}

fun Sequence<String>.day4a(): Int = sumOf { l ->
    val (left, right) = l.substringAfter(": ").split(" | ")
    val winning = "(\\d+)".toRegex().findAll(left).map { BigInteger(it.value) }.toSet()
    val have = "(\\d+)".toRegex().findAll(right).map { BigInteger(it.value) }.toSet()
    val winCount = winning.intersect(have).count()
    if (winCount < 1) {
        0
    } else {
        1 shl winCount - 1
    }
}

fun String.day4b(): Int {
    val winMap = mutableMapOf<Int, Int>()
    val lines = lines()
    lines.withIndex().forEach { (index, line) ->
        val (left, right) = line.substringAfter(": ").split(" | ")
        val winning = "(\\d+)".toRegex().findAll(left).map { BigInteger(it.value) }.toSet()
        val have = "(\\d+)".toRegex().findAll(right).map { BigInteger(it.value) }.toSet()
        val matchCount = winning.intersect(have).count()
        repeat((winMap[index] ?: 0) + 1) {
            for (i in (index + 1)..matchCount + index) {
                winMap[i] = winMap.getOrDefault(i, 0) + 1
            }
        }
    }
    return winMap.values.sum() + lines.size
}

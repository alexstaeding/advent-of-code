package aoc

fun Sequence<String>.day4a(): Int = sumOf { line ->
    when (val w = line.substringAfter(": ").split(" | ")
        .map { "(\\d+)".toRegex().findAll(it).map { x -> x.value }.toSet() }.let { (l, r) -> l.intersect(r).count() }) {
        0 -> 0
        else -> 1 shl w - 1
    }
}

fun String.day4b(): Int {
    val winMap = mutableMapOf<Int, Int>()
    val lines = lines()
    lines.withIndex().forEach { (index, line) ->
        val w = line.substringAfter(": ").split(" | ")
            .map { "(\\d+)".toRegex().findAll(it).map { x -> x.value }.toSet() }.let { (l, r) -> l.intersect(r).count() }
        repeat((winMap[index] ?: 0) + 1) {
            for (i in (index + 1)..w + index) {
                winMap[i] = winMap.getOrDefault(i, 0) + 1
            }
        }
    }
    return winMap.values.sum() + lines.size
}

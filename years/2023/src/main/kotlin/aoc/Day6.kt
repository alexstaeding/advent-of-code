package aoc

fun Sequence<String>.day6a(): Int {
    val (times, records) = map { "(\\d+)".toRegex().findAll(it.split(":")[1]).map { it.value.toInt() } }.toList()
    return times.zip(records).map { (time, record) ->
        // for each speed
        (0..time)
            .count { t -> (time - t) * t > record }
    }.reduce { a, b -> a * b }
}

fun Sequence<String>.day6b(): Int {
    val (time, record) = map { it.split(":")[1].replace("\\s+".toRegex(), "").toLong() }.toList()
    return (0..time)
        .count { t -> (time - t) * t > record }
}

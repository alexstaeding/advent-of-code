package aoc

fun Sequence<String>.day1a(): Int {
    return map { line ->
        buildString {
            append(line.first { it.isDigit() })
            append(line.last { it.isDigit() })
        }.toInt()
    }.sum()
}

private val replacements = mapOf(
    "nine" to "9",
    "eight" to "8",
    "seven" to "7",
    "six" to "6",
    "five" to "5",
    "four" to "4",
    "three" to "3",
    "two" to "2",
    "one" to "1",
)

fun Sequence<String>.day1b(): Int {
    return map { line ->
        val firstIntIndex = line.indexOfFirst { it.isDigit() }
        val firstLettersIndex = replacements.map { line.indexOf(it.key) to it.value }
            .filter { it.first >= 0 }.minByOrNull { it.first }
        val first = if (firstIntIndex >= 0 && firstIntIndex < (firstLettersIndex?.first ?: Int.MAX_VALUE)) {
            line[firstIntIndex].toString()
        } else {
            firstLettersIndex!!.second
        }

        val lastIntIndex = line.indexOfLast { it.isDigit() }
        val lastLettersIndex = replacements.map { line.lastIndexOf(it.key) to it.value }
            .filter { it.first >= 0 }.maxByOrNull { it.first }
        val last = if (lastIntIndex > (lastLettersIndex?.first ?: Int.MIN_VALUE)) {
            line[lastIntIndex].toString()
        } else {
            lastLettersIndex!!.second
        }

        (first + last).toInt()
    }.sum()
}

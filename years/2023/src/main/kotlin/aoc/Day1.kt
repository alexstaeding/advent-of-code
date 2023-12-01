package aoc

fun Sequence<String>.day1a(): Int = map { line -> "${line.first { it.isDigit() }}${line.last { it.isDigit() }}".toInt() }.sum()

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

private interface Matcher {
    fun read(c: Char): String?
}

private class PatternMatcher(private val pattern: String, private val result: String) : Matcher {
    private var matches: Int = 0

    override fun read(c: Char): String? {
        matches = when (c) {
            pattern[matches] -> matches + 1
            pattern[0] -> 1
            else -> 0
        }
        return result.takeIf { matches == pattern.length }
    }
}

private object DigitMatcher : Matcher {
    override fun read(c: Char): String? = if (c.isDigit()) c.toString() else null
}

private fun String.seek(reversed: Boolean = false): String {
    val matchers = replacements.map { (k, v) -> PatternMatcher(if (reversed) k.reversed() else k, v) } + DigitMatcher
    return firstNotNullOf { c -> matchers.firstNotNullOfOrNull { it.read(c) } }
}

fun Sequence<String>.day1b(): Int = map { "${it.seek()}${it.reversed().seek(true)}".toInt() }.sum()

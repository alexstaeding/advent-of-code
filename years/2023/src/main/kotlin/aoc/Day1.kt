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

fun interface Matcher {
    fun read(c: Char): String?
}

class PatternMatcher(private val pattern: String, private val result: String) : Matcher {
    private var matches: Int = 0

    override fun read(c: Char): String? {
        matches = when (c) {
            pattern[matches] -> matches + 1
            pattern[0] -> 1
            else -> 0
        }
        return if (matches == pattern.length) {
            matches = 0
            result
        } else {
            null
        }
    }
}

object DigitMatcher : Matcher {
    override fun read(c: Char): String? = if (c.isDigit()) c.toString() else null
}

fun String.day1b(): Int {
    val forwardMatchers = replacements.map { PatternMatcher(it.key, it.value) } + DigitMatcher
    val backwardMatchers = replacements.map { PatternMatcher(it.key.reversed(), it.value) } + DigitMatcher
    var left = 0
    var right = length - 1
    var forwardMatch: String? = null
    var backwardMatch: String? = null
    while (true) {
        if (forwardMatch == null) {
            forwardMatch = forwardMatchers.firstNotNullOfOrNull { it.read(this[left]) }
            left++
        }
        if (backwardMatch == null) {
            backwardMatch = backwardMatchers.firstNotNullOfOrNull { it.read(this[right]) }
            right--
        }
        if (forwardMatch != null && backwardMatch != null) {
            return (forwardMatch + backwardMatch).toInt()
        }
    }
}

fun Sequence<String>.day1b(): Int = map { it.day1b() }.sum()

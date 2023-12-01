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

fun interface Matcher {
    fun reset() = Unit
    fun read(c: Char): String?
}

class PatternMatcher(private val pattern: String, private val result: String) : Matcher {
    private var matches: Int = 0
    override fun reset() {
        matches = 0
    }

    override fun read(c: Char): String? {
        when (c) {
            pattern[matches] -> matches++
            pattern[0] -> matches = 1
            else -> reset()
        }
        return if (matches == pattern.length) {
            reset()
            result
        } else null
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
        }
        if (backwardMatch == null) {
            backwardMatch = backwardMatchers.firstNotNullOfOrNull { it.read(this[right]) }
        }
        if (forwardMatch != null && backwardMatch != null) {
            return (forwardMatch + backwardMatch).toInt()
        }
        if (forwardMatch == null) {
            left++
        }
        if (backwardMatch == null) {
            right--
        }
    }
}

fun Sequence<String>.day1b(): Int = map { it.day1b() }.sum()

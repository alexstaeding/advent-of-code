package aoc

fun Sequence<String>.day1a(): Int = map { line -> "${line.first { it.isDigit() }}${line.last { it.isDigit() }}".toInt() }.sum()

private val replacements = listOf("one", "two", "three", "four", "five", "six", "seven", "eight", "nine")
    .withIndex().associate { (i, s) -> s to (i + 1).toString() }

private fun interface Matcher {
    fun read(c: Char): String?

    object Digit : Matcher by Matcher({ c -> if (c.isDigit()) c.toString() else null })
    class Pattern(private val pattern: String, private val result: String) : Matcher {
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
}

fun String.seek(reversed: Boolean = false): String {
    val matchers = replacements.map { (k, v) -> Matcher.Pattern(if (reversed) k.reversed() else k, v) } + Matcher.Digit
    return (if (reversed) reversed() else this).firstNotNullOf { c -> matchers.firstNotNullOfOrNull { it.read(c) } }
}

fun Sequence<String>.day1b(): Int = map { "${it.seek()}${it.seek(true)}".toInt() }.sum()

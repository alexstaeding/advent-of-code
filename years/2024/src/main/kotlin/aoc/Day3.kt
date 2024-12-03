package aoc

private val reg = """mul\((\d+),(\d+)\)""".toRegex()

private fun String.day3a(): Int =
    reg.findAll(this).sumOf {
        val (_, a, b) = it.groupValues
        a.toInt() * b.toInt()
    }

fun Sequence<String>.day3a(): Int = sumOf { it.day3a() }

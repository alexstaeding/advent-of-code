package aoc

private val reg = """mul\((\d+),(\d+)\)""".toRegex()

fun String.day3a(): Int =
    reg.findAll(this).sumOf {
        val (_, a, b) = it.groupValues
        a.toInt() * b.toInt()
    }

fun String.day3b(): Int = split("do()")
    .map { it.substringBefore("don't()") }
    .sumOf { it.day3a() }

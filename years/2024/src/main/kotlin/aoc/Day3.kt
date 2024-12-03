package aoc

private val reg = """mul\((\d+),(\d+)\)""".toRegex()

private fun String.day3a(): Int =
    reg.findAll(this).sumOf {
        val (_, a, b) = it.groupValues
        a.toInt() * b.toInt()
    }

private fun String.day3b(): Int = split("do()")
    .map { it.substringBefore("don't()") }
    .sumOf { sec ->
        reg.findAll(sec).sumOf {
            val (_, a, b) = it.groupValues
            a.toInt() * b.toInt()
        }
    }

fun Sequence<String>.day3a(): Int = sumOf { it.day3a() }
fun Sequence<String>.day3b(): Int = joinToString("").day3b()

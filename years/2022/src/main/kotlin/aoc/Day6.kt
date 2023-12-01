package aoc

fun String.day6a(): Int = day6(4)
fun String.day6b(): Int = day6(14)
fun String.day6(count: Int): Int = (count until length).first { substring(it - count, it).toSet().size == count }

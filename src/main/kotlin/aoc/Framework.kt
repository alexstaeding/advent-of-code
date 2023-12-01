package aoc

import java.io.BufferedReader

object Framework {
    fun getInput(day: Int, level: String? = null, useExample: Boolean = false) = DayInput(day, level, useExample)
}

data class DayInput internal constructor(val day: Int, val level: String? = null, val useExample: Boolean = false)

fun DayInput.getReader(): BufferedReader {
    val filename = buildString {
        append("aoc/input$day")
        if (level != null) append("-$level")
        if (useExample) append("-example")
        append(".txt")
    }
    return checkNotNull(ClassLoader.getSystemResourceAsStream(filename)) { "$filename not found" }
        .bufferedReader()
}

fun DayInput.readText(): String = getReader().readText().trimEnd()

fun DayInput.readLines(): List<String> = getReader().readLines()

fun DayInput.lineSequence(): Sequence<String> = getReader().lineSequence()

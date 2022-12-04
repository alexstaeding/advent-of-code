package aoc

import java.io.BufferedReader

fun getReader(day: Int): BufferedReader =
    checkNotNull(ClassLoader.getSystemResourceAsStream("aoc/input$day.txt")) { "input$day.txt not found" }
        .bufferedReader()

fun getInput(day: Int): String = getReader(day).readText().trimEnd()

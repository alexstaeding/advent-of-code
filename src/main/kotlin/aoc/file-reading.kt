package aoc

fun getInput(day: Int): String =
    checkNotNull(ClassLoader.getSystemResourceAsStream("aoc/input$day.txt")) { "input$day.txt not found" }
        .reader().readText().trimEnd()

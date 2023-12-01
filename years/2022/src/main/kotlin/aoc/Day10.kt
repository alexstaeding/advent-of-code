package aoc

fun String.day10a(): Int {
    var x = 1
    var ctr = 0
    var strength = 0
    lineSequence().forEach { line ->
        if (line.startsWith("noop")) {
            ++ctr
            strength += getSignalStrength(ctr, x)
            return@forEach
        }
        val (op, arg) = line.split(" ")
        when (op) {
            "addx" -> {
                ++ctr
                strength += getSignalStrength(ctr, x)
                ++ctr
                strength += getSignalStrength(ctr, x)
                x += arg.toInt()
            }
            else -> error("unknown op $op")
        }
    }
    return strength
}

fun String.day10b() {
    var x = 1
    var ctr = 0
    lineSequence().forEach { line ->
        if (line.startsWith("noop")) {
            printPixel(ctr, x)
            ++ctr
            if (ctr % 40 == 0) {
                println()
            }
            return@forEach
        }
        val (op, arg) = line.split(" ")
        when (op) {
            "addx" -> {
                printPixel(ctr, x)
                ++ctr
                if (ctr % 40 == 0) {
                    println()
                }
                printPixel(ctr, x)
                ++ctr
                if (ctr % 40 == 0) {
                    println()
                }
                x += arg.toInt()
            }

            else -> error("unknown op $op")
        }
    }
}

fun getSignalStrength(ctr: Int, x: Int): Int {
    return if ((ctr + 20) % 40 == 0) {
        ctr * x
    } else {
        0
    }
}

fun printPixel(ctr: Int, x: Int) {
    // get pixel position in row
    val pixel = ctr % 40
    if (pixel in setOf(-1, 0, 1).map { it + x }) {
        print("#")
    } else {
        print(".")
    }
}

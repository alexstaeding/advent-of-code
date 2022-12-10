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

fun getSignalStrength(ctr: Int, x: Int): Int {
    return if ((ctr+20) % 40 == 0) {
        ctr * x
    } else 0
}

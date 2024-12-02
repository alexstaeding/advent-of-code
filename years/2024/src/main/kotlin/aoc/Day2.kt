package aoc

fun Sequence<String>.day2a(): Int {
    return count { line ->
        val nums = line.split(" ")
            .map { it.toInt() }

        val decreasing = (nums[0] - nums[1]) > 0

        nums.windowed(2)
            .all { (a, b) ->
                if (decreasing) {
                    (a - b >= 1) && (a - b <= 3)
                } else {
                    (b - a >= 1) && (b - a <= 3)
                }
            }
    }
}

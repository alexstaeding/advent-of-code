package aoc

private fun checkValid(nums: List<Int>): Boolean {
    val decreasing = (nums[0] - nums[1]) > 0

    return nums.windowed(2)
        .all { (a, b) ->
            if (decreasing) {
                (a - b >= 1) && (a - b <= 3)
            } else {
                (b - a >= 1) && (b - a <= 3)
            }
        }
}

fun Sequence<String>.day2a(): Int =
    count { line ->
        val nums = line.split(" ")
            .map { it.toInt() }

        checkValid(nums)
    }

fun Sequence<String>.day2b(): Int =
    count { line ->
        val nums = line.split(" ")
            .map { it.toInt() }

        nums.indices.map {
            // remove index it from nums
            val newNums = nums.toMutableList()
            newNums.removeAt(it)
            newNums
        }.any {
            checkValid(it)
        }
    }

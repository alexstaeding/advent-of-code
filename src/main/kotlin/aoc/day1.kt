package aoc

fun main() {
    val lines = checkNotNull(ClassLoader.getSystemResourceAsStream("aoc/input1.txt")) { "input1.txt not found" }
        .bufferedReader()
        .readLines()

    val max = IntArray(3)
    var curr = 0
    for (line in lines) {
        if (line == "") {
            if (max.any { curr > it }) {
                max.sort()
                max[0] = curr
            }
            curr = 0
        } else {
            curr += line.toInt()
        }
    }

    println(max.joinToString(","))
    println(max.sum())
}

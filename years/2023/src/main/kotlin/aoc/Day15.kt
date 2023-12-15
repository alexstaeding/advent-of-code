package aoc

fun main() {
    Framework.getInput(15, useExample = false).readText().day15b().let { println(it) }
}

private fun String.doHash(): Int {
    var current = 0
    for (c in this) {
        current += c.code
        current *= 17
        current %= 256
    }
    return current
}

fun String.day15a(): Int = split(",").sumOf { it.doHash() }

fun String.day15b(): Int {
    val boxes = MutableList(256) { mutableMapOf<String, Int>() }
    fun printBoxes() {
        boxes.withIndex().filter { (_, v) -> v.isNotEmpty() }.forEach { (index, box) ->
            println(box.entries.joinToString(" ", "Box $index: ") { (label, num) -> "[$label $num]" })
        }
    }
    for (instruction in split(",")) {
        val (box, op, num) = Regex("([a-z]+)([=\\-])(\\d*)").find(instruction)!!.destructured
        val boxNum = box.doHash()
        assert(boxNum in 0..255)
        when (op) {
            "=" -> {
                boxes[boxNum][box] = num.toInt()
            }
            "-" -> {
                boxes[boxNum].remove(box)
            }
            else -> error("Oopsi")
        }
//        println("After \"$instruction\":")
//        printBoxes()
//        println()
    }

    return boxes.withIndex().sumOf { (boxIndex, box) ->
        box.values.withIndex().sumOf { (slotIndex, lens) ->
//            println("Box $boxIndex, slot $slotIndex: $lens -> ${1 + boxIndex} * ${1 * slotIndex} * $lens")
            (1 + boxIndex) * (1 + slotIndex) * lens
        }
    }
}

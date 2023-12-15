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
    }

    return boxes.withIndex().sumOf { (boxIndex, box) ->
        box.values.withIndex().sumOf { (slotIndex, lens) ->
            (1 + boxIndex) * (1 + slotIndex) * lens
        }
    }
}

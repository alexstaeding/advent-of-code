package aoc

private typealias Grid14 = Array<CharArray>

private data class Pos14(val y: Int, val x: Int)

fun main() {
    Framework.getInput(14, useExample = false).readLines().day14a().let { println(it) }
}

fun List<Char>.calculateWeights(): Int {
    val adjusted = indices.asSequence()
        .drop(1)
        .fold(this) { acc, index ->
            if (acc[index] == 'O') {
                val squareSupport = acc.slice(0..<index)
                    .lastIndexOf('#').takeIf { it >= 0 } ?: 0

                val newPos = acc.slice(squareSupport..<index)
                    .indexOf('.')

                if (newPos >= 0) {
                    acc.toMutableList().also {
                        it[newPos + squareSupport] = 'O'
                        it[index] = '.'
                    }
                } else acc
            } else acc
        }
    return adjusted.withIndex().sumOf { (i, c) -> (adjusted.size - i).takeIf { c == 'O' } ?: 0 }
}

fun List<String>.day14a(): Int {
    val grid = map { it.toCharArray() }.toTypedArray()
    return grid[0].indices.sumOf { col ->
        grid.map { it[col] }.calculateWeights()
    }
}

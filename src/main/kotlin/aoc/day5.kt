package aoc

fun String.day5a(): String {
    val (commands: List<String>, initialState: State) = lineSequence()
        .partition { it.startsWith("move") }
        .let { (commands, state) -> commands to state.readState().map { it } }
    initialState.print()
    val endState = commands.fold(initialState) { state, command -> state.applyCommand(command) }
    return endState.map { it.last() }.joinToString("")
}

typealias State = List<List<Char>>

fun State.print() = forEach { println(it) }

val cmdRegex = "move (?<count>[1-9][0-9]*) from (?<from>[1-9][0-9]*) to (?<to>[1-9][0-9]*)".toRegex()

fun State.applyCommand(cmd: String): State {
    val (count, from, to) = checkNotNull(cmdRegex.matchEntire(cmd)).groupValues.drop(1).map { it.toInt() }
    return move(count, from - 1, to - 1)
}

fun State.move(count: Int, from: Int, to: Int): State {
    require(count != 0)
    return if (count > 1) {
        move(count - 1, from, to).move(1, from, to)
    } else {
        mapIndexed { index, chars ->
            when (index) {
                from -> chars.dropLast(1)
                to -> chars + this[from].last()
                else -> chars
            }
        }
    }
}

/**
 * series k = 3, 7, 11, 15, 19
 * k = 4 * n + 3
 */
fun Int.toK(): Int = 4 * this + 3

/**
 * reverse function to index n
 * series n = 0, 1, 2, 3, 4
 * n = (k - 3) / 4
 */
fun Int.toN(): Int = (this - 3) / 4

fun State.addRow(rowBelow: List<Char?>): List<List<Char?>> {
    return rowBelow.indices.map {
        listOf(rowBelow[it]) + (getOrNull(it) ?: emptyList())
    }
}

// investigate:
// fun State.addRow(rowBelow: List<Char?>): List<List<Char>> {
//    return rowBelow.indices.mapNotNull {
//        rowBelow.getOrNull(it)?.let { char -> listOf(char) + (getOrNull(it) ?: emptyList())}
//    }
//}

fun List<String>.readState(): State {
    return takeWhile { it[1] != '1' }.fold(emptyList()) { stateAbove, newRow ->
        stateAbove.addRow(newRow.getCols()).map { it.filterNotNull() }
    }
}

fun String.getCols(): List<Char?> {
    // get indices of letter characters (in k)
    val indices = mapIndexedNotNull { i, c -> (i + 1).takeIf { c == '[' } }.toSet()
    // fill stacks of each column
    return (0..length.toN()).map { n ->
        val k = n.toK() - 2 // move from right edge of col to begin of char
        if (k in indices) {
            this[k]
        } else null
    }
}

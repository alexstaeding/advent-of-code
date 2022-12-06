package aoc

typealias State = List<List<Char>>
typealias MoveCmd = State.(count: Int, from: Int, to: Int) -> State
val cmdRegex = "move (?<count>[1-9][0-9]*) from (?<from>[1-9][0-9]*) to (?<to>[1-9][0-9]*)".toRegex()

fun String.day5a(): String = day5(State::move)
fun String.day5b(): String = day5(State::moveTogether)

private fun String.day5(moveCmd: MoveCmd): String {
    val (commands: List<String>, initialState: State) = lineSequence()
        .partition { it.startsWith("move") }
        .let { (commands, state) -> commands to state.readState().map { it } }
    return commands
        .fold(initialState) { state, command -> state.applyCommand(command, moveCmd) }
        .map { it.last() }
        .joinToString("")
}

fun State.applyCommand(cmd: String, moveCmd: MoveCmd): State {
    val (count, from, to) = checkNotNull(cmdRegex.matchEntire(cmd)).groupValues.drop(1).map { it.toInt() }
    return moveCmd(count, from - 1, to - 1)
}

fun State.move(count: Int, from: Int, to: Int): State = if (count > 1) {
    move(count - 1, from, to).move(1, from, to)
} else {
    mapIndexed { i, c ->
        when (i) {
            from -> c.dropLast(1)
            to -> c + this[from].last()
            else -> c
        }
    }
}

fun State.moveTogether(count: Int, from: Int, to: Int): State = mapIndexed { i, c ->
    when (i) {
        from -> c.dropLast(count)
        to -> c + this[from].takeLast(count)
        else -> c
    }
}

fun State.addRow(r: List<Char?>): List<List<Char>> = r.mapIndexed { i, c ->
    c?.let { listOf(it) + (getOrNull(i) ?: emptyList()) } ?: emptyList()
}

fun List<String>.readState(): State = takeWhile { it[1] != '1' }.fold(emptyList()) { s, r ->
    s.addRow(r.getCols())
}

fun String.getCols() = mapIndexedNotNull { i, c -> (i + 1).takeIf { c == '[' } }.toSet().let { indices ->
    (0..(length - 3) / 4).map { n ->
        val k = 4 * n + 1
        if (k in indices) this[k] else null
    }
}

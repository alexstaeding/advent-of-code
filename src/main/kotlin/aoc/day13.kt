package aoc

fun main() {
    getInput(13).day13().forEach {
        println(it)
    }
}

// list of indices of pairs that are in the right order
fun String.day13(): List<Int> {
    return split("\n\n").mapIndexedNotNull { id, group ->
        val (first, second) = group.lines().map { it.parseBrackets() }
        if (first < second) id + 1 else null
    }
}

fun String.parseBrackets(position: Int): Pair<ListElement, Int> {
    val list = mutableListOf<ListElement>()
    var i = position
    while (i < length) {
        when (get(i)) {
            '[' -> {
                val (subList, nextI) = parseBrackets(i + 1)
                list.add(subList)
                i = nextI
            }
            ']' -> return SubList(list) to i
            ',' -> Unit
            else -> list.add(Value(substring(i).substringBefore(',').substringBefore(']').toInt()))
        }
        ++i
    }
    return list.first() to i
}

fun String.parseBrackets(): ListElement = parseBrackets(0).first

sealed interface ListElement : Comparable<ListElement>
data class SubList(val elements: List<ListElement>) : ListElement {
    override fun toString(): String = elements.joinToString(", ", "[", "]")
    override fun compareTo(other: ListElement): Int {
        val result = when (other) {
            is SubList -> {
                elements
                    .zip(other.elements) { left, right -> left.compareTo(right) }
                    .firstOrNull { it != 0 }
                    ?: elements.size.compareTo(other.elements.size)
            }

            is Value -> compareTo(SubList(listOf(other)))
        }
        println("compared $this ${if (result < 0) "<" else if (result == 0) "==" else ">"} $other")
        return result
    }
}

data class Value(val value: Int) : ListElement {
    override fun toString(): String = value.toString()
    override fun compareTo(other: ListElement): Int {
        val result = when (other) {
            is SubList -> SubList(listOf(this)).compareTo(other)
            is Value -> value.compareTo(other.value)
        }
        println("compared $this ${if (result < 0) "<" else if (result == 0) "==" else ">"} $other")
        return result
    }
}

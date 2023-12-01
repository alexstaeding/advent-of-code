package aoc

fun String.day13a(): Int {
    return split("\n\n").mapIndexedNotNull { id, group ->
        val (first, second) = group.lines().map { it.parseBrackets() }
        if (first < second) id + 1 else null
    }.sum()
}

fun String.day13b(): Int {
    val bracketsFromList = lines()
        .filter { it.isNotBlank() }
        .map { it.parseBrackets() }
    val e2 = "[[2]]".parseBrackets()
    val e6 = "[[6]]".parseBrackets()
    val sorted = (bracketsFromList + e2 + e6).sorted()
    return sorted.mapIndexedNotNull { i, it ->
        if (it == e2 || it == e6) {
            i + 1
        } else {
            null
        }
    }.fold(1) { a, b -> a * b }
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
            else -> {
                val num = substring(i).substringBefore(',').substringBefore(']')
                list.add(Value(num.toInt()))
                i += num.length - 1
            }
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
        return result
    }
}

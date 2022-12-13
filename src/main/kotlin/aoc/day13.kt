package aoc

fun String.parseBrackets(position: Int): Pair<ListElement, Int> {
    val list = mutableListOf<ListElement>()
    var i = position
    while (i < length) {
        when (val c = get(i)) {
            '[' -> {
                val (subList, nextI) = parseBrackets(i + 1)
                list.add(subList)
                i = nextI
            }

            ']' -> return SubList(list) to i
            ',' -> {
                // do nothing
            }

            else -> {
                list.add(Value(c.toString().toInt()))
            }
        }
        ++i
    }
    return list.first() to i
}

fun String.parseBrackets(): ListElement = parseBrackets(0).first

sealed interface ListElement
data class SubList(val elements: List<ListElement>) : ListElement {
    override fun toString(): String = elements.joinToString(", ", "[", "]")
}

data class Value(val value: Int) : ListElement {
    override fun toString(): String = value.toString()
}

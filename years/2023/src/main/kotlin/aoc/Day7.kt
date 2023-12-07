package aoc

sealed interface Kind {
    fun matches(s: String): Boolean

    data object FiveOfAKind : Kind {
        override fun matches(s: String): Boolean =
            s.all { it == s[0] }
    }

    data object FourOfAKind : Kind {
        override fun matches(s: String): Boolean =
            s.toSet().any { c -> s.count { c == it } == 4 }
    }

    data object FullHouse : Kind {
        override fun matches(s: String): Boolean =
            s.toSet().let {
                it.any { c -> s.count { c == it } == 3 } &&
                    it.any { c -> s.count { c == it } == 2 }
            }
    }

    data object ThreeOfAKind : Kind {
        override fun matches(s: String): Boolean =
            s.toSet().any { c -> s.count { c == it } == 3 }
    }

    data object TwoPair : Kind {
        override fun matches(s: String): Boolean =
            s.toSet().count { c -> s.count { c == it } == 2 } == 2
    }

    data object OnePair : Kind {
        override fun matches(s: String): Boolean =
            s.toSet().any { c -> s.count { c == it } == 2 }
    }

    data object HighCard : Kind {
        override fun matches(s: String): Boolean = true
    }
}

val cards = listOf('A', 'K', 'Q', 'J', 'T', '9', '8', '7', '6', '5', '4', '3', '2').reversed()
    .withIndex().associate { (i, v) -> v to i }
val hands =
    listOf(Kind.HighCard, Kind.OnePair, Kind.TwoPair, Kind.ThreeOfAKind, Kind.FullHouse, Kind.FourOfAKind, Kind.FiveOfAKind)
        .withIndex().associate { (i, v) -> v to i }

val cardComparator: Comparator<Char> = Comparator.comparing { cards[it] ?: -1 }
val handComparator: Comparator<String> = Comparator.comparing<String, Int> {
    hands[it.getHandKind()] ?: -1
}.then { a, b ->
    (0..<5).firstNotNullOfOrNull { (cards[a[it]] ?: -1).compareTo(cards[b[it]] ?: -1).takeIf { x -> x != 0 } }
        ?: 0
}

fun String.getHandKind(): Kind = hands.filter { it.key.matches(this) }.maxBy { it.value }.key

fun main() {
    Framework.getInput(7, useExample = false).lineSequence().day7a().let { println(it) }
}

fun Sequence<String>.day7a(): Int = map { it.split(" ") }
    .associate { (hand, bid) -> hand to bid.toInt() }
    .toSortedMap(handComparator)
    .asSequence()
    .withIndex()
    .onEach { (i, e) -> println("${i + 1} * ${e.value} ${e.key}") }
    .sumOf { (i, e) -> (i + 1) * e.value }

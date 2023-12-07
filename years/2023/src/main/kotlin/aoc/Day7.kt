package aoc

fun interface Kind {
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
val cardsWithJoker = listOf('A', 'K', 'Q', 'T', '9', '8', '7', '6', '5', '4', '3', '2', 'J').reversed()
    .withIndex().associate { (i, v) -> v to i }
val hands = listOf(Kind.HighCard, Kind.OnePair, Kind.TwoPair, Kind.ThreeOfAKind, Kind.FullHouse, Kind.FourOfAKind, Kind.FiveOfAKind)
    .withIndex().associate { (i, v) -> v to i }

val handsWithJoker = hands.mapKeys { (k, _) -> Kind { cards.keys.any { j -> k.matches(it.replace('J', j)) } } }

fun handComparator(hands: Map<out Kind, Int>, cards: Map<Char, Int>): Comparator<String> = Comparator.comparing<String, Int> {
    hands[it.getHandKind(hands)] ?: -1
}.then { a, b ->
    (0..<5).firstNotNullOfOrNull { (cards[a[it]] ?: -1).compareTo(cards[b[it]] ?: -1).takeIf { x -> x != 0 } }
        ?: 0
}

fun String.getHandKind(hands: Map<out Kind, Int>): Kind = hands.filter { it.key.matches(this) }.maxBy { it.value }.key

private fun Sequence<String>.day7(hands: Map<out Kind, Int>, cards: Map<Char, Int>): Int = map { it.split(" ") }
    .associate { (hand, bid) -> hand to bid.toInt() }
    .toSortedMap(handComparator(hands, cards))
    .asSequence()
    .withIndex()
    .onEach { (i, e) -> println("${i + 1} * ${e.value} ${e.key}") }
    .sumOf { (i, e) -> (i + 1) * e.value }

fun Sequence<String>.day7a() = day7(hands, cards)
fun Sequence<String>.day7b() = day7(handsWithJoker, cardsWithJoker)

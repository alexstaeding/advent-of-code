package aoc

fun interface Kind {
    fun matches(s: String): Boolean
}

val cards = setOf('2', '3', '4', '5', '6', '7', '8', '9', 'T', 'J', 'Q', 'K', 'A')
    .withIndex().associate { (i, v) -> v to i }
val cardsWithJoker = (setOf('J') + cards.keys)
    .withIndex().associate { (i, v) -> v to i }

val hands = listOf(
    Kind { true },
    Kind { s -> s.toSet().any { c -> s.count { c == it } == 2 } },
    Kind { s -> s.toSet().count { c -> s.count { c == it } == 2 } == 2 },
    Kind { s -> s.toSet().any { c -> s.count { c == it } == 3 } },
    Kind { s -> s.toSet().let { x -> x.any { c -> s.count { c == it } == 3 } && x.any { c -> s.count { c == it } == 2 } } },
    Kind { s -> s.toSet().any { c -> s.count { c == it } == 4 } },
    Kind { s -> s.toSet().all { it == s[0] } },
).withIndex().associate { (i, v) -> v to i }

val handsWithJoker = hands.mapKeys { (k, _) -> Kind { cards.keys.any { j -> k.matches(it.replace('J', j)) } } }

fun <V> compareHands(hands: Map<out Kind, Int>, cards: Map<Char, Int>): Comparator<Pair<String, V>> =
    Comparator.comparing<Pair<String, V>, Int> { (s, _) ->
        hands.filter { it.key.matches(s) }.maxOf { it.value }
    }.then { (a, _), (b, _) ->
        (0..<5).firstNotNullOfOrNull { (cards[a[it]] ?: -1).compareTo(cards[b[it]] ?: -1).takeIf { x -> x != 0 } } ?: 0
    }

private fun Sequence<String>.day7(hands: Map<out Kind, Int>, cards: Map<Char, Int>): Int = map { it.split(" ") }
    .map { (hand, bid) -> hand to bid.toInt() }
    .sortedWith(compareHands(hands, cards))
    .map { (_, v) -> v }
    .withIndex()
    .sumOf { (i, v) -> (i + 1) * v }

fun Sequence<String>.day7a() = day7(hands, cards)
fun Sequence<String>.day7b() = day7(handsWithJoker, cardsWithJoker)

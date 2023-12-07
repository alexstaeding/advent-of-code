package aoc

private typealias KindFilter = (String) -> Boolean

private val cards = setOf('2', '3', '4', '5', '6', '7', '8', '9', 'T', 'J', 'Q', 'K', 'A')

private val hands: List<KindFilter> = listOf(
    { true },
    { s -> s.toSet().any { c -> s.count { c == it } == 2 } },
    { s -> s.toSet().count { c -> s.count { c == it } == 2 } == 2 },
    { s -> s.toSet().any { c -> s.count { c == it } == 3 } },
    { s -> s.toSet().let { x -> x.any { c -> s.count { c == it } == 3 } && x.any { c -> s.count { c == it } == 2 } } },
    { s -> s.toSet().any { c -> s.count { c == it } == 4 } },
    { s -> s.toSet().all { it == s[0] } },
)

private fun Sequence<String>.day7(hands: List<KindFilter>, cards: Set<Char>): Int = map { it.split(" ") }
    .map { (hand, bid) -> hand to bid.toInt() }
    .sortedWith(
        Comparator.comparing<Pair<String, Int>, Int> { (s, _) ->
            hands.withIndex().filter { (_, k) -> k(s) }.maxOf { it.index }
        }.then { (a, _), (b, _) ->
            (0..<5).firstNotNullOfOrNull { i -> cards.indexOf(a[i]).compareTo(cards.indexOf(b[i])).takeIf { it != 0 } } ?: 0
        },
    )
    .map { (_, v) -> v }
    .withIndex()
    .sumOf { (i, v) -> (i + 1) * v }

fun Sequence<String>.day7a() = day7(hands, cards)
fun Sequence<String>.day7b() = day7(hands.map { k -> { cards.any { j -> k(it.replace('J', j)) } } }, setOf('J') + cards)

import aoc.Framework
import aoc.lineSequence
import java.math.BigInteger

fun main() {
    Framework.getInput(4, useExample = false).lineSequence().day4a().let { println(it) }
}

fun Sequence<String>.day4a(): BigInteger = withIndex().sumOf { (i, l) ->
    val (left, right) = l.substringAfter(": ").split(" | ")
    val winning = "(\\d+)".toRegex().findAll(left).map { BigInteger(it.value) }.toSet()
    val have = "(\\d+)".toRegex().findAll(right).map { BigInteger(it.value) }
    val winCount = winning.intersect(have.toSet()).count()
    if (winCount < 1) {
        BigInteger.ZERO
    } else {
        BigInteger.TWO.pow(winCount - 1)
    }
}

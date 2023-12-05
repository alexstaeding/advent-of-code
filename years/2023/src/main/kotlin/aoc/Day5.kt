package aoc

fun main() = Framework.getInput(5, useExample = false).readLines().day5a().let { println(it) }

private data class Mapping(
    val dest: Long,
    val source: Long,
    val count: Long,
)

private fun String.toMapping(): Mapping {
    val (dest, source, count) = split(" ").map { it.toLong() }
    return Mapping(dest, source, count)
}

private fun processMappings(nums: List<Long>, mappings: List<Mapping>): List<Long> {
    return nums.map { n ->
        mappings.firstOrNull { (_, s, c) -> n in s..s + c }?.let { mapping ->
            (n - mapping.source) + mapping.dest
        } ?: n
    }
}

fun List<String>.day5a(): Long {
    val seeds = this[0].split(": ")[1].split(" ").map { it.toLong() }
    val parts = asSequence().drop(2).joinToString("\n").split("\n\n")
        .map { it.substringAfter("\n") }
        .map { it.split("\n").map { l -> l.toMapping() } }

    return parts.fold(seeds) { acc, mappings -> processMappings(acc, mappings) }.min()
}

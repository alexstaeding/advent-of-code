package aoc

import kotlin.math.max
import kotlin.math.min

private data class Mapping(
    val dest: Long,
    val source: Long,
    val count: Long,
) {
    val destRange = dest..<dest + count
    val sourceRange = source..<source + count
    val destDelta = dest - source

    override fun toString(): String = "$dest $source $count"
}

fun LongRange.intersects(other: LongRange): Boolean = this.first <= other.last && other.first <= this.last

private fun String.toMapping(): Mapping {
    val (dest, source, count) = split(" ").map { it.toLong() }
    return Mapping(dest, source, count)
}

fun List<String>.day5a(): Long {
    val seeds = this[0].split(": ")[1].split(" ").map { it.toLong() }
    val parts = asSequence().drop(2).joinToString("\n").split("\n\n")
        .map { it.substringAfter("\n") }
        .map { it.split("\n").map { l -> l.toMapping() } }

    return parts.fold(seeds) { nums, mappings ->
        nums.map { n ->
            mappings.firstOrNull { (_, s, c) -> n in s..s + c }?.let { mapping ->
                (n - mapping.source) + mapping.dest
            } ?: n
        }
    }.min()
}

private fun LongRange.mapWith(mappings: List<Mapping>): List<LongRange> {
    return mappings.fold(listOf(this)) { acc, mapping ->
        acc.flatMap {
            buildList {
                // before source range
                if (it.first < mapping.sourceRange.first) {
                    add(it.first..min(it.last, mapping.sourceRange.first - 1))
                }
                // in source range
                if (it.intersects(mapping.sourceRange)) {
                    add(max(it.first + mapping.destDelta, mapping.dest)..min(it.last + mapping.destDelta, mapping.destRange.last))
                }
                // after source range
                if (it.last > mapping.sourceRange.last) {
                    add(max(it.first, mapping.sourceRange.last + 1)..it.last)
                }
            }
        }
    }
}

fun List<String>.day5b(): Long {
    val seeds = "(\\d+) (\\d+)".toRegex().findAll(this[0].split(": ")[1])
        .map { it.destructured }
        .map { (a, b) -> a.toLong().let { x -> x..x + b.toLong() } }
        .toList()

    val parts = asSequence().drop(2).joinToString("\n").split("\n\n")
        .map { it.substringAfter("\n") }
        .map { it.split("\n").map { l -> l.toMapping() } }

    return parts.fold(seeds) { nums, mappings ->
        nums.flatMap { n ->
            n.mapWith(mappings)
        }
    }.minOf { it.first }
}

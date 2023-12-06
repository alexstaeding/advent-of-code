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

fun List<String>.day5a(): Long = asSequence().drop(2).joinToString("\n").split("\n\n")
    .map { it.substringAfter("\n") }
    .map { it.split("\n").map { l -> l.toMapping() } }.fold(
        // input seeds
        this[0].split(": ")[1].split(" ").map { it.toLong() },
    ) { nums, mappings ->
        nums.map { n ->
            mappings.firstOrNull { (_, s, c) -> n in s..s + c }?.let { mapping ->
                (n - mapping.source) + mapping.dest
            } ?: n
        }
    }.min()

fun List<String>.day5b(): Long = asSequence().drop(2).joinToString("\n").split("\n\n")
    .map { it.substringAfter("\n") }
    .map { it.split("\n").map { l -> l.toMapping() } }.fold(
        // input seeds
        "(\\d+) (\\d+)".toRegex().findAll(this[0].split(": ")[1])
            .map { it.destructured }
            .map { (a, b) -> a.toLong().let { x -> x..x + b.toLong() } }
            .toList(),
    ) { nums, mappings ->
        mappings.fold(nums to listOf<LongRange>()) { (untransformed, transformed), mapping ->
            untransformed.flatMap {
                buildList {
                    // before source range
                    if (it.first < mapping.sourceRange.first) {
                        add(it.first..min(it.last, mapping.sourceRange.first - 1))
                    }

                    // after source range
                    if (it.last > mapping.sourceRange.last) {
                        add(max(it.first, mapping.sourceRange.last + 1)..it.last)
                    }
                }
            } to transformed + untransformed.filter { it.intersects(mapping.sourceRange) }.map {
                max(it.first + mapping.destDelta, mapping.dest)..min(it.last + mapping.destDelta, mapping.destRange.last)
            }
        }.let { (a, b) -> a + b }
    }.minOf { it.first }

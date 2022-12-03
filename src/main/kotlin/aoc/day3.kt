package aoc

fun String.day3a(): Int = splitToSequence("\n").map { line ->
    val (left, right) = line.withIndex().partition { it.index < line.length / 2 }
    left.map { it.value }
        .intersect(right.map { it.value }.toSet())
        .sumOf { it.toScore() }
}.sum()

fun String.day3b(): Int = splitToSequence("\n")
    .map { it.toSet() }
    .chunked(3) { (a, b, c) -> a.intersect(b).intersect(c).single().toScore() }
    .sum()

fun Char.toScore() = if (code > 96) code - 96 else code - 38

// golfed versions of b
// 142: fun String.b()=split("\n").map{it.toSet()}.chunked(3){(a,b,c)->a.intersect(b).intersect(c).first().run{if(code>96)code-96 else code-38}}.sum()
// 154: println(java.io.File("a").readLines().map{it.toSet()}.chunked(3){(a,b,c)->a.intersect(b).intersect(c).first().run{if(code>96)code-96 else code-38}}.sum())

package aoc

sealed interface FileSystemNode {
    val name: String
    val size: Int
}

data class File(override val name: String, override val size: Int) : FileSystemNode

data class Directory(override val name: String) : FileSystemNode {
    val children = mutableListOf<FileSystemNode>()
    override val size: Int by lazy { children.sumOf { it.size } }
}

fun parseDirectory(rootName: String, lines: Iterator<String>): Directory {
    val dir = Directory(rootName)
    lines.assertNext("$ ls")
    while (lines.hasNext()) {
        val next = lines.next()
        if (next == "$ cd ..") {
            break
        } else if (next.startsWith("$ cd")) {
            val childName = next.substringAfter("$ cd ")
            dir.children += parseDirectory(childName, lines)
            continue
        }
        val (size, name) = next.split(" ", limit = 2)
        if (size != "dir") {
            dir.children += File(name, size.toInt())
        }
    }
    return dir
}

fun String.parse(): Directory {
    val iterator = splitToSequence("\n").iterator()
    iterator.assertNext("$ cd /")
    return parseDirectory("/", iterator)
}

fun Directory.print(indent: String = "") {
    println("$indent$name")
    children.forEach {
        when (it) {
            is Directory -> it.print("$indent  ")
            is File -> println("$indent  ${it.name} ${it.size}")
        }
    }
}

fun Directory.collectChildren(map: MutableMap<String, FileSystemNode>, parentPath: String = "") {
    map["$parentPath/$name"] = this
    children.forEach {
        when (it) {
            is Directory -> it.collectChildren(map, "$parentPath/$name")
            is File -> map["$parentPath/$name/${it.name}"] = it
        }
    }
}

fun Iterator<String>.assertNext(expected: String) {
    val next = next()
    check(next == expected) { "Expected '$expected' but got '$next'" }
}

fun main() {
    val root = getInput(7).parse()
    val allDirs = mutableMapOf<String, FileSystemNode>()
    root.collectChildren(allDirs)

    val smallDirs = allDirs.values.filterIsInstance<Directory>().filter { it.size <= 100_000 }
    println("Part A: ${smallDirs.sumOf { it.size }}")

    val totalDiskSpace = 70_000_000
    val neededUnusedSpace = 30_000_000
    val initialFreeSpace = totalDiskSpace - root.size
    val neededSpace = neededUnusedSpace - initialFreeSpace
    val smallestDir = allDirs.values
        .filterIsInstance<Directory>()
        .filter { it.size > neededSpace }
        .minBy { it.size }
    println("Part B: ${smallestDir.size}")
}

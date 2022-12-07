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

fun Directory.traverseDirs(): Sequence<FileSystemNode> {
    val queue = ArrayDeque<FileSystemNode>()
    queue.add(this)
    return sequence {
        while (queue.isNotEmpty()) {
            val node = queue.removeFirst()
            yield(node)
            if (node is Directory) {
                node.children.filterIsInstanceTo<Directory, _>(queue)
            }
        }
    }
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

fun String.day7a(): Int = parse().traverseDirs().filter { it.size <= 100_000 }.sumOf { it.size }

const val totalDiskSpace = 70_000_000
const val neededUnusedSpace = 30_000_000

fun String.day7b(): Int {
    val root = parse()
    val neededSpace = neededUnusedSpace - (totalDiskSpace - root.size)
    return root.traverseDirs().filter { it.size > neededSpace }.minOf { it.size }
}

package aoc


fun String.day7a(): Int = parse().traverseDirs().filter { it.size <= 100_000 }.sumOf { it.size }

const val totalDiskSpace = 70_000_000
const val neededUnusedSpace = 30_000_000

fun String.day7b(): Int {
    val root = parse()
    val neededSpace = neededUnusedSpace - (totalDiskSpace - root.size)
    return root.traverseDirs().filter { it.size > neededSpace }.minOf { it.size }
}

sealed interface FileSystemNode {
    val name: String
    val size: Int
}

data class File(override val name: String, override val size: Int) : FileSystemNode

data class Directory(override val name: String) : FileSystemNode {
    val children = mutableListOf<FileSystemNode>()
    override val size: Int by lazy { children.sumOf { it.size } }
}

fun String.parse() =
    splitToSequence("\n").iterator().apply { assertNext("$ cd /") }.parseDir("/")

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

fun Iterator<String>.parseDir(rootName: String): Directory {
    val dir = Directory(rootName)
    assertNext("$ ls")
    while (hasNext()) {
        val next = next()
        if (next == "$ cd ..") {
            break
        } else if (next.startsWith("$ cd")) {
            val childName = next.substringAfter("$ cd ")
            dir.children += parseDir(childName)
            continue
        }
        val (size, name) = next.split(" ", limit = 2)
        if (size != "dir") {
            dir.children += File(name, size.toInt())
        }
    }
    return dir
}

fun Iterator<String>.assertNext(expected: String) =
    next().let { check(it == expected) { "Expected '$expected' but got '$it'" } }

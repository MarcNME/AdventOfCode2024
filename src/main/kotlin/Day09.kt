import java.io.File

fun main() {
    //val input = File("./inputs/day09-example.txt").readText()
    val input = File("./inputs/day09.txt").readText()
    val disk = input.readBlocks()
    val compactedDisk = disk.toMutableList()
    compactedDisk.compact()
    println("Checksum of compacted disk: ${compactedDisk.calculateChecksum()}")

    val unfragmentedDisk = disk.toMutableList()
    unfragmentedDisk.defragment()
    println("Checksum of unfragmented disk: ${unfragmentedDisk.calculateChecksum()}")
}

private interface Block
private class EmptyBlock() : Block {
    override fun toString() = "."
}

private class FullBlock(val id: Int) : Block {
    override fun toString() = id.toString()
}

private fun String.readBlocks(): MutableList<Block> {
    var isFile = true
    var id = 0
    val disk = mutableListOf<Block>()
    forEach {
        if (!it.isDigit()) return@forEach
        val length = it.digitToInt()
        (0..<length).forEach { _ ->
            if (isFile) {
                disk.add(FullBlock(id))
            } else {
                disk.add(EmptyBlock())
            }
        }
        if (isFile) id++
        isFile = !isFile
    }
    return disk
}

private fun List<Block>.getIndexOfFirstEmptyBlock() = indexOfFirst { it.javaClass == EmptyBlock::class.java }
private fun List<Block>.getIndexOfLastFullBlock() = indexOfLast { it.javaClass == FullBlock::class.java }

private fun MutableList<Block>.compact() {
    var isCompacted = false
    while (!isCompacted) {
        val indexOfFirstEmptyBlock = getIndexOfFirstEmptyBlock()
        val indexOfLastFullBlock = getIndexOfLastFullBlock()

        if (indexOfFirstEmptyBlock > indexOfLastFullBlock) {
            isCompacted = true
        } else {
            this[indexOfFirstEmptyBlock] = this[indexOfLastFullBlock]
            this[indexOfLastFullBlock] = EmptyBlock()
        }
    }
}

private fun List<Block>.calculateChecksum(): Long {
    var sum = 0L
    forEachIndexed { index, block ->
        if (block is FullBlock) {
            sum += block.id * index
        }
    }
    return sum
}

private fun List<Block>.getBiggestID(): Int {
    var biggestId = 0
    forEach { if (it is FullBlock && it.id > biggestId) biggestId = it.id }
    return biggestId
}

private fun List<Block>.getStartOfFreeSpace(size: Int): Int {
    var space = 0
    var beginOfEmptySpace = 0
    forEachIndexed { index, block ->
        if (block is EmptyBlock) {
            if (space++ == 0)
                beginOfEmptySpace = index
            if (space == size)
                return beginOfEmptySpace
        } else {
            space = 0
        }
    }
    return -1
}

private fun MutableList<Block>.defragment() {
    var id = getBiggestID()

    while (id >= 0) {
        val indexOfFirstOccurrence = indexOfFirst { it is FullBlock && it.id == id }
        val fileSize =
            this.indexOfLast { it is FullBlock && it.id == id } - indexOfFirstOccurrence + 1
        val startOfFreeSpace = getStartOfFreeSpace(fileSize)

        if (startOfFreeSpace in 1..<indexOfFirstOccurrence) {
            replaceAll { if (it is FullBlock && it.id == id) EmptyBlock() else it }
            (0..<fileSize).forEach { index ->
                this[startOfFreeSpace + index] = FullBlock(id)
            }
        }

        id--
    }
}

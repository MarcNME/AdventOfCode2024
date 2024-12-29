import java.io.File

fun main() {
    //val input = File("./inputs/day09-example.txt").readText()
    val input = File("./inputs/day09.txt").readText()
    val disk = input.readBlocks()
    disk.compact()
    println(disk.calculateChecksum())
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

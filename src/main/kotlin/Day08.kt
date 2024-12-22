fun main() {
    //val input = readInput("day08-example.txt").map { it.toList() }
    val input = readInput("day08.txt").map { it.toList() }

    val symbols = input.getUniqueSymbols()
    val antinodes = mutableSetOf<Pair<Int, Int>>()
    val antinodesWithHarmonics = mutableSetOf<Pair<Int, Int>>()
    val bounds = input.first().indices to input.indices

    symbols.forEach { symbol ->
        val positions = input.findPositionsForSymbol(symbol)
        antinodes.addAll(positions.calculateAntiNodes())
        antinodesWithHarmonics.addAll(positions.calculateAntiNodesWithHarmonics(bounds))
    }

    antinodes.removeOutOfBounds(bounds)

    println("Antinodes: ${antinodes.size}")
    println("Antinodes including harmonics: ${antinodesWithHarmonics.size}")
}

private fun List<List<Char>>.getUniqueSymbols(): Set<Char> {
    val symbols = mutableSetOf<Char>()
    this.forEach { symbols.addAll(it) }
    symbols.removeAll(listOf('.', '#'))
    return symbols
}

private fun List<List<Char>>.findPositionsForSymbol(c: Char): List<Pair<Int, Int>> {
    val positions = mutableListOf<Pair<Int, Int>>()

    for (y in 0..this.lastIndex) {
        for (x in 0..this[y].lastIndex) {
            if (this[y][x] == c) {
                positions.add(x to y)
            }
        }
    }

    return positions
}

private fun List<Pair<Int, Int>>.calculateAntiNodes(): Set<Pair<Int, Int>> {
    if (this.size == 1) {
        return emptySet()
    }

    val x = this.toMutableList()
    val first = x.removeFirst()
    val antinodes = mutableSetOf<Pair<Int, Int>>()

    x.forEach {
        val distanceX = first.first - it.first
        val distanceY = first.second - it.second

        antinodes.add((first.first + distanceX) to (first.second + distanceY))
        antinodes.add((it.first - distanceX) to (it.second - distanceY))
    }
    return antinodes + x.calculateAntiNodes()
}

private fun MutableSet<Pair<Int, Int>>.removeOutOfBounds(bounds: Pair<IntRange, IntRange>) {
    val toRemove = mutableSetOf<Pair<Int, Int>>()
    forEach {
        if (it.first !in bounds.first || it.second !in bounds.second) toRemove.add(it)
    }
    removeAll(toRemove)
}

private fun List<Pair<Int, Int>>.calculateAntiNodesWithHarmonics(bounds: Pair<IntRange, IntRange>): Set<Pair<Int, Int>> {
    if (this.size == 1) {
        return emptySet()
    }

    val x = this.toMutableList()
    val first = x.removeFirst()
    val antinodesWithHarmonics = mutableSetOf<Pair<Int, Int>>()

    x.forEach {
        val distanceX = first.first - it.first
        val distanceY = first.second - it.second
        antinodesWithHarmonics.add(first)
        antinodesWithHarmonics.add(it)

        var tmp = mutableSetOf<Pair<Int, Int>>()

        while (true) {
            val antinode = if (tmp.isEmpty()) {
                (first.first + distanceX) to (first.second + distanceY)
            } else {
                (tmp.last().first + distanceX) to (tmp.last().second + distanceY)
            }
            if (antinode.first !in bounds.first || antinode.second !in bounds.second) break
            tmp.add(antinode)
        }
        antinodesWithHarmonics.addAll(tmp)
        tmp = mutableSetOf()

        while (true) {
            val antinode = if (tmp.isEmpty()) {
                (it.first - distanceX) to (it.second - distanceY)
            } else {
                (tmp.last().first - distanceX) to (tmp.last().second - distanceY)
            }
            if (antinode.first !in bounds.first || antinode.second !in bounds.second) break
            tmp.add(antinode)
        }

        antinodesWithHarmonics.addAll(tmp)
    }
    return antinodesWithHarmonics + x.calculateAntiNodesWithHarmonics(bounds)
}
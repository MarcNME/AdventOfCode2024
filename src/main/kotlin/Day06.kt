fun main() {
    //val input = readInput("day06-example.txt").map { it.toCharArray().toMutableList() }.toMutableList()
    val input = readInput("day06.txt").map { it.toCharArray().toMutableList() }.toMutableList()

    val solved = part1(input)
    part2(input, solved)
}

private fun part1(input: MutableList<MutableList<Char>>): List<List<Char>> {
    val field = input.createCopy()
    while (field.any { chars -> chars.any { char -> Direction.arrowList.contains(char) } }) {
        field.getArrowPosition()?.let { (x, y) ->
            val direction = Direction.fromArrow(field[y][x])
            when (direction) {
                Direction.UP -> {
                    field[y][x] = 'X'
                    if (y - 1 in field.indices) {
                        if (y - 2 in field.indices && field[y - 2][x] == '#') {
                            field[y - 1][x] = '>'
                        } else {
                            field[y - 1][x] = '^'
                        }
                    }
                }

                Direction.DOWN -> {
                    field[y][x] = 'X'
                    if (y + 1 in field.indices) {
                        if (y + 2 in field.indices && field[y + 2][x] == '#') {
                            field[y + 1][x] = '<'
                        } else {
                            field[y + 1][x] = 'v'
                        }
                    }
                }

                Direction.LEFT -> {
                    field[y][x] = 'X'
                    if (x - 1 in field.first().indices) {
                        if (x - 2 in field.first().indices && field[y][x - 2] == '#') {
                            field[y][x - 1] = '^'
                        } else {
                            field[y][x - 1] = '<'
                        }
                    }
                }

                Direction.RIGHT -> {
                    field[y][x] = 'X'
                    if (x + 1 in field.first().indices) {
                        if (x + 2 in field.first().indices && field[y][x + 2] == '#') {
                            field[y][x + 1] = 'v'
                        } else {
                            field[y][x + 1] = '>'
                        }
                    }
                }
            }
        }
    }

    println(field.countXes())
    return field
}

private fun part2(input: MutableList<MutableList<Char>>, solved: List<List<Char>>) {
    var count = 0
    var tries = 0
    for (i in input.indices) {
        for (j in input.first().indices) {
            if (solved[i][j] == 'X' && input[j][j + 1] == '.') {
                tries++
                val field = input.createCopy()
                field[i][j] = '#'
                if (tries == 1885) {
                    println(field)
                    println("$i to $j")
                }
                if (field.isLooping()) count++
                println("Tries: $tries Count: $count")
                //field.printField()
            }
        }
    }

    println(count)
}

private fun MutableList<MutableList<Char>>.isLooping(): Boolean {
    var finished = false
    while (!finished) {
        getArrowPosition()?.let { (x, y) ->
            val direction = Direction.fromArrow(this[y][x])
            when (direction) {
                Direction.UP -> {
                    this[y][x] = '⬆'
                    if (y - 1 in indices) {
                        if (y - 2 in indices && this[y - 2][x] == '#') {
                            this[y - 1][x] = '>'
                        } else if (y - 2 in first().indices && this[y - 2][x] == '⬆') {
                            return true
                        } else {
                            this[y - 1][x] = '^'
                        }
                    } else finished = true
                }

                Direction.DOWN -> {
                    this[y][x] = '⬇'
                    if (y + 1 in indices) {
                        if (y + 2 in indices && this[y + 2][x] == '#') {
                            this[y + 1][x] = '<'
                        } else if (y + 2 in first().indices && this[y + 2][x] == '⬇') {
                            return true
                        } else {
                            this[y + 1][x] = 'v'
                        }
                    } else finished = true
                }

                Direction.LEFT -> {
                    this[y][x] = '⬅'
                    if (x - 1 in first().indices) {
                        if (x - 2 in first().indices && this[y][x - 2] == '#') {
                            this[y][x - 1] = '^'
                        } else if (x - 2 in first().indices && this[y][x - 2] == '⬅') {
                            return true
                        } else {
                            this[y][x - 1] = '<'
                        }
                    } else finished = true
                }

                Direction.RIGHT -> {
                    this[y][x] = '➡'
                    if (x + 1 in first().indices) {
                        if (x + 2 in first().indices && this[y][x + 2] == '#') {
                            this[y][x + 1] = 'v'
                        } else if (x + 2 in first().indices && this[y][x + 2] == '➡') {
                            return true
                        } else {
                            this[y][x + 1] = '>'
                        }
                    } else finished = true
                }
            }
        }
    }
    return false
}

private fun List<List<Char>>.getArrowPosition(): Pair<Int, Int>? {
    val y = indexOfFirst { it.any { char -> Direction.arrowList.contains(char) } }
    if (y == -1) return null
    val x = this[y].indexOfFirst { char -> Direction.arrowList.contains(char) }
    return x to y
}

private fun List<List<Char>>.countXes() = sumOf { it.count { c -> c == 'X' } }
private fun List<List<Char>>.printField() {
    forEach {
        println(it)
    }
    println()
}

private fun List<List<Char>>.createCopy() = map { it.toMutableList() }.toMutableList()

private enum class Direction(val arrow: Char) {
    UP('^'), DOWN('v'), LEFT('<'), RIGHT('>');

    companion object {
        val arrowList = listOf('<', '>', '^', 'v')
        infix fun fromArrow(arrow: Char) = entries.first { it.arrow == arrow }
    }
}
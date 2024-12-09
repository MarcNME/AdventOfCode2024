fun main() {
    val input = readInput("day04.txt").map { it.toCharArray() }
    part1(input)
    part2(input)
}

fun part1(input: List<CharArray>) {
    var count = 0
    for (i in input.indices) {
        for (j in input[i].indices) {

            if (input[i][j] == 'X') {
                //Vertically left to right ➡️
                if (j + 3 in input[i].indices && input[i][j + 1] == 'M' && input[i][j + 2] == 'A' && input[i][j + 3] == 'S') {
                    count++
                }

                //Vertically right to left ⬅️
                if (j - 3 in input[i].indices && input[i][j - 1] == 'M' && input[i][j - 2] == 'A' && input[i][j - 3] == 'S') {
                    count++
                }

                //Horizontally top to bottom ⬇️
                if (i - 3 in input.indices && input[i - 1][j] == 'M' && input[i - 2][j] == 'A' && input[i - 3][j] == 'S') {
                    count++
                }

                //Horizontally bottom to top ⬆️
                if (i + 3 in input.indices && input[i + 1][j] == 'M' && input[i + 2][j] == 'A' && input[i + 3][j] == 'S') {
                    count++
                }

                //Diagonally top left to bottom right ↘️
                if (i + 3 in input.indices && j + 3 in input[i].indices && input[i + 1][j + 1] == 'M' && input[i + 2][j + 2] == 'A' && input[i + 3][j + 3] == 'S') {
                    count++
                }

                //Diagonally top right to bottom left ↙️
                if (i + 3 in input.indices && j - 3 in input[i].indices && input[i + 1][j - 1] == 'M' && input[i + 2][j - 2] == 'A' && input[i + 3][j - 3] == 'S') {
                    count++
                }

                //Diagonally bottom left to top right ↗️
                if (i - 3 in input.indices && j + 3 in input[i].indices && input[i - 1][j + 1] == 'M' && input[i - 2][j + 2] == 'A' && input[i - 3][j + 3] == 'S') {
                    count++
                }

                //Diagonally bottom right to top left↖️
                if (i - 3 in input.indices && j - 3 in input[i].indices && input[i - 1][j - 1] == 'M' && input[i - 2][j - 2] == 'A' && input[i - 3][j - 3] == 'S') {
                    count++
                }
            }
        }
    }

    println("'XMAS' occurs $count times")
}

fun part2(input: List<CharArray>) {
    var count = 0

    for (i in 1..input.size - 2) {
        for (j in 1..input[i].size - 2) {
            if (input[i][j] == 'A') {
                if (input[i - 1][j - 1] == 'M' && input[i - 1][j + 1] == 'M' && input[i + 1][j - 1] == 'S' && input[i + 1][j + 1] == 'S') {
                    count++
                } else if (input[i - 1][j - 1] == 'S' && input[i - 1][j + 1] == 'S' && input[i + 1][j - 1] == 'M' && input[i + 1][j + 1] == 'M') {
                    count++
                } else if (input[i - 1][j - 1] == 'M' && input[i - 1][j + 1] == 'S' && input[i + 1][j - 1] == 'M' && input[i + 1][j + 1] == 'S') {
                    count++
                } else if (input[i - 1][j - 1] == 'S' && input[i - 1][j + 1] == 'M' && input[i + 1][j - 1] == 'S' && input[i + 1][j + 1] == 'M') {
                    count++
                }
            }
        }
    }

    println("There are $count X-MASes")
}
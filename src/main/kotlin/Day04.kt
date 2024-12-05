fun main() {
    val input = readInput("day04.txt").map { it.toCharArray() }
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

    println(count)
}
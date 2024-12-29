fun main() {
    val input = readInput("day02.txt")

    val numbers = input.map { line -> line.split(' ').map { it.toInt() } }

    var numSafeReports = 0
    var numSaveWithDampener = 0

    numbers.forEach { report ->
        if (report.isSafe()) {
            numSafeReports++
            println("$report without removing any level")
        } else if (report.isSaveWithDampener()) {
            numSaveWithDampener++
        }

    }

    println("Safe reports: $numSafeReports")
    println("Safe with dampener: ${numSafeReports + numSaveWithDampener}")
}

private fun List<Int>.isSaveWithDampener(): Boolean {
    for (i in this.indices) {
        val mutableReport = this.toMutableList()
        mutableReport.removeAt(i)
        if (mutableReport.isSafe()) {
            println("$this is save by removing ${this[i]}")
            return true
        }
    }
    return false
}

private fun List<Int>.levelsDecreasing(): Boolean {
    for (i in this.indices) {
        if (i == 0) {
            continue
        }
        val diff = this[i - 1] - this[i]
        if (diff !in 1..3) {
            return false
        }
    }
    return true
}

private fun List<Int>.levelsIncreasing(): Boolean {
    for (i in this.indices) {
        if (i == 0) {
            continue
        }
        val diff = this[i] - this[i - 1]
        if (diff !in 1..3) {
            return false
        }
    }
    return true
}

private fun List<Int>.isSafe(): Boolean {
    return this.levelsDecreasing() || this.levelsIncreasing()
}
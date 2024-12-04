fun main() {
    val input = readInput("day02.txt")

    val numbers = input.map { line -> line.split(' ').map { it.toInt() } }

    var numSafeReports = 0

    numbers.forEach { report ->
        println("$report Safe decreasing: ${report.levelsDecreasing()} Safe increasing: ${report.levelsIncreasing()}")
        if (report.isSafe()) {
            numSafeReports++
        }
    }

    println("Safe reports: $numSafeReports")
}

fun List<Int>.levelsDecreasing(): Boolean {
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

fun List<Int>.levelsIncreasing(): Boolean {
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

fun List<Int>.isSafe(): Boolean {
    return this.levelsDecreasing() || this.levelsIncreasing()
}
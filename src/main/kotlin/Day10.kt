fun main() {
    //val input = readInput("day10-example.txt").map { it.toCharArray() }
    val input = readInput("day10.txt").map { it.toCharArray() }
    val trailheadScore = input.getTrailheadScoreSum()

    println("Trailhead score sum: $trailheadScore")

    val trailheadRatingSum = input.getTrailheadRatingSum()
    println("Trailhead rating sum: $trailheadRatingSum")
}

private fun List<CharArray>.getTrailheadScoreSum(): Int {

    var trailheadScore = 0
    for (y in first().indices) {
        for (x in indices) {
            if (this[y][x] == '0')
                trailheadScore += getTrailheadScore(x, y)
        }
    }
    return trailheadScore
}

private fun List<CharArray>.getTrailheadScore(
    x: Int,
    y: Int,
    level: Char = '0',
    hikedToPeaks: MutableList<Pair<Int, Int>> = mutableListOf(),
): Int {
    var score = 0
    if (this[y][x] == '9') {
        if ((x to y) in hikedToPeaks) {
            return 0
        }
        hikedToPeaks.add(x to y)
        return 1
    }
    if (x + 1 in first().indices && this[y][x + 1] == level + 1)
        score += getTrailheadScore(x + 1, y, level + 1, hikedToPeaks)
    if (x - 1 in first().indices && this[y][x - 1] == level + 1)
        score += getTrailheadScore(x - 1, y, level + 1, hikedToPeaks)
    if (y + 1 in indices && this[y + 1][x] == level + 1)
        score += getTrailheadScore(x, y + 1, level + 1, hikedToPeaks)
    if (y - 1 in indices && this[y - 1][x] == level + 1)
        score += getTrailheadScore(x, y - 1, level + 1, hikedToPeaks)
    return score
}

private fun List<CharArray>.getTrailheadRatingSum(): Int {
    var rating = 0
    for (y in first().indices) {
        for (x in indices) {
            if (this[y][x] == '0')
                rating += getTrailheadRating(x, y)
        }
    }
    return rating
}

private fun List<CharArray>.getTrailheadRating(
    x: Int,
    y: Int,
    level: Char = '0',
): Int {
    var score = 0
    if (this[y][x] == '9') {
        return 1
    }
    if (x + 1 in first().indices && this[y][x + 1] == level + 1)
        score += getTrailheadRating(x + 1, y, level + 1)
    if (x - 1 in first().indices && this[y][x - 1] == level + 1)
        score += getTrailheadRating(x - 1, y, level + 1)
    if (y + 1 in indices && this[y + 1][x] == level + 1)
        score += getTrailheadRating(x, y + 1, level + 1)
    if (y - 1 in indices && this[y - 1][x] == level + 1)
        score += getTrailheadRating(x, y - 1, level + 1)
    return score
}
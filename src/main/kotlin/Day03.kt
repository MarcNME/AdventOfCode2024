import java.io.File
import java.util.regex.Pattern

fun main() {
    val input = File("inputs/day03.txt").readText()
    part1(input)
    part2(input)
}

private fun part1(input: String) {
    val regex = "mul\\(\\d{1,3},\\d{1,3}\\)".toRegex()

    val matches = regex.findAll(input)
    val sum = matches.sumOf { match ->
        match.value.removePrefix("mul(").removeSuffix(")").split(',').map { it.toInt() }.mul()
    }

    println("Sum of valid mul instructions: $sum")
}

private fun part2(input: String) {
    val mulPattern = Pattern.compile("mul\\(\\d{1,3},\\d{1,3}\\)")
    val doPattern = Pattern.compile("do\\(\\)")
    val dontPattern = Pattern.compile("don't\\(\\)")

    var enabled = true
    var sum = 0
    var i = 0
    while (i in input.indices) {
        val subStr = input.substring(startIndex = i)

        val dontMatch = dontPattern.matcher(subStr)
        if (dontMatch.lookingAt()) {
            enabled = false
            i += dontMatch.end()
            continue
        }

        val doMatch = doPattern.matcher(subStr)
        if (doMatch.lookingAt()) {
            enabled = true
            i += doMatch.end()
            continue
        }

        val mulMatch = mulPattern.matcher(subStr)
        if (mulMatch.lookingAt() && enabled) {
            val x = mulMatch.group()
            i += mulMatch.end()
            sum += x
                .removePrefix("mul(")
                .removeSuffix(")")
                .split(',')
                .map { it.toInt() }
                .mul()
            continue
        }
        i++
    }

    println("Sum of valid and enabled mul instructions: $sum")
}

private fun List<Int>.mul(): Int {
    return this[0] * this[1]
}
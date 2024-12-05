import java.io.File

fun main() {
    val input = File("inputs/day03.txt").readText()

    val regex = "mul\\(\\d{1,3},\\d{1,3}\\)".toRegex()
    val matches = regex.findAll(input)
    val sum = matches.sumOf { match ->
        match.value.removePrefix("mul(").removeSuffix(")").split(',').map { it.toInt() }.mul()
    }

    println("Sum of valid mul instructions: $sum")
}

private fun List<Int>.mul(): Int {
    return this[0] * this[1]
}
import kotlin.math.pow

fun main() {
    //val input = readInput("day07-example.txt").map { line -> line.split(' ').map { it.removeSuffix(":").toLong() } }
    val input = readInput("day07.txt").map { line -> line.split(' ').map { it.removeSuffix(":").toLong() } }
    part1(input)
    part2(input)
}

private fun part1(input: List<List<Long>>) {
    var sum = 0L

    input.forEach {
        val expected = it.component1().toLong()
        val operants = it.subList(1, it.size)

        val possibleOperators = generateAllPossibleOperators(operants)


        for(operators in possibleOperators) {
            var product = operants.first()
            operators.forEachIndexed { i, operator ->
                product = operator.calc(product, operants[i+1])
            }
            if (product == expected) {
                sum += expected
                break
            }
        }
    }

    println("$sum")
}

private fun part2(input: List<List<Long>>) {
    var sum = 0L

    input.forEach {
        val expected = it.component1().toLong()
        val operants = it.subList(1, it.size)

        val possibleOperators = generateAllPossibleOperatorsWithConcat(operants)


        for(operators in possibleOperators) {
            var product = operants.first()
            operators.forEachIndexed { i, operator ->
                product = operator.calc(product, operants[i+1])
            }
            if (product == expected) {
                sum += expected
                break
            }
        }
    }

    println("$sum")
}

private abstract class Operant {
    abstract fun calc(n: Long, m: Long): Long
}

private class Plus() : Operant() {
    override fun calc(n: Long, m: Long): Long = n + m
    override fun toString(): String = "+"
}

private class Multiple() : Operant() {
    override fun calc(n: Long, m: Long): Long = n * m
    override fun toString(): String = "*"
}

private class Concat() : Operant() {
    override fun calc(n: Long, m: Long): Long = (n.toString() + m.toString()).toLong()
    override fun toString(): String = "||"
}

private fun createOperantList(number: Int, operantCount: Int): List<Operant> {
    if (number == 0) return List(operantCount) { Plus() }

    val list = mutableListOf<Operant>()
    var n = number

    while (n > 0) {
        list.addFirst(if (n % 2 == 0) Plus() else Multiple())
        n /= 2
    }

    for (i in 1..operantCount - list.size) {
        list.addFirst(Plus())
    }

    return list
}

private fun generateAllPossibleOperators(operants: List<Long>): List<List<Operant>> {
    val possibilities = 2.0.pow(operants.size - 1).toInt()

    val possibleOperators: MutableList<List<Operant>> = mutableListOf()

    for (i in 0..<possibilities) {
        possibleOperators.add(createOperantList(i, operants.size - 1))
    }
    return possibleOperators
}

private fun createOperantListWithConcat(number: Int, operantCount: Int): List<Operant> {
    if (number == 0) return List(operantCount) { Plus() }

    val list = mutableListOf<Operant>()
    var n = number

    while (n > 0) {
        list.addFirst(when (n % 3) {
            0 -> Plus()
            1 -> Multiple()
            else -> Concat()
        })
        n /= 3
    }

    for (i in 1..operantCount - list.size) {
        list.addFirst(Plus())
    }

    return list
}

private fun generateAllPossibleOperatorsWithConcat(operants: List<Long>): List<List<Operant>> {
    val possibilities = 3.0.pow(operants.size - 1).toInt()

    val possibleOperators: MutableList<List<Operant>> = mutableListOf()

    for (i in 0..<possibilities) {
        possibleOperators.add(createOperantListWithConcat(i, operants.size - 1))
    }
    return possibleOperators
}
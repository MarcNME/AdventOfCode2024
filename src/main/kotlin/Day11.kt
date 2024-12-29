import java.math.BigInteger

private val ZERO = BigInteger.valueOf(0)
private val ONE = BigInteger.valueOf(1)
private val TWO_THOUSAND_TWENTY_FOUR: BigInteger = BigInteger.valueOf(2024)

fun main() {
    var input = readInput("day11.txt").flatMap { it.split(' ').map { str -> str.toBigInteger() } }

    for (i in 1..25) {
        input = input.blink()
        println("We have ${input.size} stones after $i blinks")
    }
}

private fun BigInteger.evenNumberOfDigits(): Boolean = toString().length % 2 == 0

private fun List<BigInteger>.blink(): List<BigInteger> {
    val copy = toMutableList()
    var indexOffset = 0
    forEachIndexed { index, stone ->
        if (stone == ZERO) {
            copy[index + indexOffset] = ONE
        } else if (stone.evenNumberOfDigits()) {
            val stoneStr = stone.toString()
            val leftHalf = stoneStr.substring(0..<stoneStr.length / 2)
            val rightHalf = stoneStr.substring(stoneStr.length / 2)
            copy[index + indexOffset] = leftHalf.toBigInteger()
            copy.add(index + ++indexOffset, rightHalf.toBigInteger())
        } else {
            copy[index + indexOffset] = stone.times(TWO_THOUSAND_TWENTY_FOUR)
        }
    }
    return copy
}
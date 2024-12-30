import java.math.BigInteger
import kotlin.time.measureTime

private val ZERO = BigInteger.valueOf(0)
private val ONE = BigInteger.valueOf(1)
private val TWO_THOUSAND_TWENTY_FOUR: BigInteger = BigInteger.valueOf(2024)

fun main() {
    val input = readInput("day11.txt").flatMap { it.split(' ').map { str -> str.toBigInteger() } }
    var stones1 = input.toList()
    for (i in 1..25) {
        val duration1 = measureTime { stones1 = stones1.blink() }
        println("We have ${stones1.size} stones after $i blinks. Took ${duration1.inWholeMilliseconds}ms")
    }
    input.process()
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

private fun List<BigInteger>.process() {
    var stones = associate { it to 1L }
    var tmp = mutableMapOf<BigInteger, Long>()
    for (i in 1..75) {
        val duration = measureTime {
            stones.forEach { (stone, count) ->
                if (stone == ZERO) {

                    tmp[ONE] = tmp.getOrDefault(ONE, 0) + count
                } else if (stone.evenNumberOfDigits()) {
                    val stoneStr = stone.toString()
                    val leftHalf = stoneStr.substring(0..<stoneStr.length / 2).toBigInteger()
                    val rightHalf = stoneStr.substring(stoneStr.length / 2).toBigInteger()

                    tmp[leftHalf] = tmp.getOrDefault(leftHalf, 0) + count
                    tmp[rightHalf] = tmp.getOrDefault(rightHalf, 0) + count
                } else {
                    val newStone = stone.times(TWO_THOUSAND_TWENTY_FOUR)

                    tmp[newStone] = tmp.getOrDefault(newStone, 0) + count
                }
            }
            stones = tmp.toMap()
            tmp.clear()
        }
        println("We have ${stones.values.sum()} stones after $i blinks. Took ${duration.inWholeMilliseconds}ms")
    }
    println("We have ${stones.values.sum()} stones")
}
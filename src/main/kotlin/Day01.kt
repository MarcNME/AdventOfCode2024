import kotlin.math.abs

fun main() {
    val input = readInput("day01.txt")

    val firstList = mutableListOf<Int>()
    val secondList = mutableListOf<Int>()

    input.forEach {
        firstList.add(it.substringBefore(' ').toInt())
        secondList.add(it.substringAfterLast(' ').toInt())
    }

    firstList.sort()
    secondList.sort()

    getTotalDistance(firstList, secondList)
    getSimilarity(firstList, secondList)
}

fun getTotalDistance(firstList: List<Int>, secondList: List<Int>) {
    if (firstList.size != secondList.size){
        error("Lists are different lengths")
    }
    var sum = 0

    for (i in firstList.indices){
        val distance = abs(firstList[i] - secondList[i])
        println("${firstList[i]}  ${secondList[i]}   $distance")
        sum += distance
    }
    println()
    println(sum)
}

fun getSimilarity(firstList: List<Int>, secondList: List<Int>) {
    if (firstList.size != secondList.size){
        error("Lists are different lengths")
    }

    var similarity = 0

    firstList.forEach { firstNumber ->
        similarity += secondList.count { it == firstNumber } * firstNumber
    }

    println("Similarity: $similarity")
}
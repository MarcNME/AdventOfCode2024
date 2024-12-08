fun main() {
    //val lines = readInput("day05-example.txt")
    val lines = readInput("day05.txt")
    val rules = mutableListOf<Pair<Int, Int>>()
    val updates = mutableListOf<List<Int>>()

    var isRule = true
    for (line in lines) {
        if (line.isEmpty()) {
            isRule = false
            continue
        }
        if (isRule) {
            val rule = line.split('|')
            rules.add(rule.first().toInt() to rule.last().toInt())
        } else {
            updates.add(line.split(',').map { it.toInt() })
        }
    }

    val splitUpdates = updates.partition { updateIsValid(it, rules) }
    val validUpdates = splitUpdates.first
    val invalidUpdates = splitUpdates.second

    println("Sum of valid updates " + validUpdates.sumOf { it.getMiddleElement() })
    println("Sum of fixed updates " + invalidUpdates.sumOf { it.fixUpdate(rules).getMiddleElement() })
}

private fun updateIsValid(update: List<Int>, rules: List<Pair<Int, Int>>): Boolean {
    update.forEachIndexed { i, page ->
        if (rules.filter { (first, _) -> first == page }.any { (_, second) -> update.subList(0, i).contains(second) })
            return false
    }
    return true
}

private fun List<Int>.getMiddleElement() = this[this.size / 2]

private fun List<Int>.fixUpdate(rules: List<Pair<Int, Int>>): ArrayList<Int> {
    val update = ArrayList(this)
    if (!updateIsValid(this,rules)) {

        this.forEachIndexed { i, page ->
            val subList = subList(0, i)
            val rulesThatApply = rules.filter { (first, _) -> first == page }

            subList.forEach {
                if (rulesThatApply.any { (_, second) -> second == it }) {
                    update.remove(it)
                    update.add(i, it)
                }
            }
        }
        return update.fixUpdate(rules)
    } else {
        return update
    }
}
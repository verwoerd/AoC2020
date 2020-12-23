package day23.part2

import day23.part1.findDestination
import day23.part1.readInput
import java.io.BufferedReader

/**
 * @author verwoerd
 * @since 23/12/2020
 */
fun part2(input: BufferedReader): Any {
  val max = 1_000_000
  val helperCup = Cup(label = -1) // Helper node to make the list circulair
  var start: Int // the label of the cup we are going to start with

  // Create a lookup map for each cup by their label
  val cups =
    (10..max).toCollection(input.readInput().also { start = it.first }).runningFold(helperCup) { acc, i ->
      val right = Cup(leftCup = acc, label = i)
      acc.rightCup = right
      right
    }.map { it.label to it }.filter { it.first != -1 }.toMap()
  helperCup.right.connectLeft(cups.getValue(max))
  helperCup.connectLeft(helperCup)

  var pointer = cups.getValue(start)
  repeat(10_000_000) {
    pointer.playRound(cups, max)
    pointer++
  }

  val target = cups.getValue(1)
  return target.right.label.toLong() * target.right.right.label
}


data class Cup(var leftCup: Cup? = null, var rightCup: Cup? = null, var label: Int) {
  val left: Cup
    get() = leftCup ?: error("No previous connection for node $label")
  val right: Cup
    get() = rightCup ?: error("No next connection for node $label")

  operator fun inc() = right

  fun connectLeft(other: Cup) {
    other.rightCup = this
    leftCup = other
  }

  fun playRound(cups: Map<Int, Cup>, max: Int) {
    // pickup 3 cups
    val pickup1 = right
    val pickup2 = pickup1.right
    val pickup3 = pickup2.right

    // remove the three out of the chain, keeping the remaining one in a circle
    pickup3.right.connectLeft(pickup1.left)

    // determine the destination cup
    val destinationCup = cups.getValue(findDestination(label, max, pickup1.label, pickup2.label, pickup3.label))

    // Insert the three pickup cups  picked up between destination cup and its neighbour
    destinationCup.right.connectLeft(pickup3)
    pickup1.connectLeft(destinationCup)
  }


  override fun toString() =
    generateSequence(this) { current -> current.right.takeIf { it.label != label } }
      .map { it.label }
      .joinToString(" ")
}

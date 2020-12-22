package day22.part2

import day22.part1.Player
import day22.part1.readInput
import java.io.BufferedReader

/**
 * @author verwoerd
 * @since 22/12/2020
 */
fun part2(input: BufferedReader): Any {
  val (player1, player2) = input.readInput()
  val winner = player1.resolveWinnerRecursive(player2)
  return winner.score
}

fun Player.resolveWinnerRecursive(other: Player): Player {
  val previous = mutableSetOf<List<Int>>()
  val otherPrevious = mutableSetOf<List<Int>>()
  generateSequence { recursiveCombatRound(other) }
    .first { !previous.add(deck) || !otherPrevious.add(other.deck) || deck.isEmpty() || other.deck.isEmpty() }
  return when {
    deck.isEmpty() -> other
    else -> this
  }
}

private fun Player.createCopy(card: Int) = copy(deck = deck.take(card).toMutableList())

private fun Player.recursiveCombatRound(other: Player) {
  val myCard = drawCard()
  val otherCard = other.drawCard()
  when {
    myCard <= deck.size && otherCard <= other.deck.size -> {
      // play sub game
      val myCopy = createCopy(myCard)
      val otherCopy = other.createCopy(otherCard)
      val winner = myCopy.resolveWinnerRecursive(otherCopy)
      when (winner.id) {
        id -> addWinningCards(myCard, otherCard)
        else -> other.addWinningCards(otherCard, myCard)
      }
    }
    else -> determineWinner(other, myCard, otherCard)
  }
}

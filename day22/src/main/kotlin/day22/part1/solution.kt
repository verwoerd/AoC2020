package day22.part1

import java.io.BufferedReader

/**
 * @author verwoerd
 * @since 22/12/2020
 */
fun part1(input: BufferedReader): Any {
  val (player1, player2) = input.readInput()
  val winner = player1.resolveWinner(player2)
  return winner.score
}

fun BufferedReader.readInput(): Pair<Player, Player> {
  val (_, player1, player2) = lineSequence().fold(
    Triple(
      0,
      Player(1),
      Player(2)
          )
                                                 ) { (phase, player1, player2), current ->
    when {
      current.isBlank() -> Triple(1, player1, player2)
      current.startsWith("Player") -> Triple(phase, player1, player2)
      phase == 0 -> Triple(phase, player1.also { it.addCard(current.toInt()) }, player2)
      else -> Triple(phase, player1, player2.also { it.addCard(current.toInt()) })
    }
  }
  return player1 to player2
}

data class Player(
  val id: Int,
  val deck: MutableList<Int> = mutableListOf(),
                 ) {
  val score
    get() = deck.reversed().foldIndexed(0) { index, acc, i -> acc + ((index + 1) * i) }

  fun drawCard() = deck.removeFirst()
  fun addCard(card: Int) = deck.add(card)
  fun addWinningCards(winner: Int, loser: Int) = deck.add(winner) && deck.add(loser)
  fun determineWinner(other: Player, myCard: Int, otherCard: Int) {
    when {
      myCard > otherCard -> addWinningCards(myCard, otherCard)
      else -> other.addWinningCards(otherCard, myCard)
    }
  }

  private fun doRound(other: Player) {
    val myCard = drawCard()
    val otherCard = other.drawCard()
    determineWinner(other, myCard, otherCard)
  }

  fun resolveWinner(other: Player): Player =
    generateSequence { doRound(other) }.first { deck.isEmpty() || other.deck.isEmpty() }.let {
      when {
        deck.isEmpty() -> other
        else -> this
      }
    }
}

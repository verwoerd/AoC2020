package day16.part1

import day16.part1.TicketConstraint.Companion.parseLine
import java.io.BufferedReader

/**
 * @author verwoerd
 * @since 16/12/2020
 */
fun part1(input: BufferedReader): Any {
  return input.parseInput().let { (ranges, _, nearbyTickets) ->
    nearbyTickets.flatMap { ticket ->
      ticket.filter { number -> ranges.all { range -> number !in range } }
    }.sum()
  }
}

data class TicketConstraint(
  val label: String,
  val constraints: List<IntRange>
                           ) {
  companion object {
    fun parseLine(line: String): TicketConstraint {
      val (label, constraints) = line.split(": ")
      return (TicketConstraint(label, constraints.split(" or ").map { remainder ->
        val (low, up) = remainder.split("-").map { it.toInt() }
        (low..up)
      }))
    }
  }

  infix operator fun contains(value: Int) = constraints.any { value in it }
}

typealias Ticket = List<Int>

fun BufferedReader.parseInput() = lineSequence().fold(
  0 to Triple(
    listOf<TicketConstraint>(),
    listOf<Int>() as Ticket,
    listOf<Ticket>()
             )
                                                     ) { (phase, triple), line ->
  val (range, ticket, nearby) = triple
  when (phase) { // phase indicates how far we are in the input
    0 -> when (line) {
      "" -> phase + 1 to triple
      else -> phase to Triple(
        range + parseLine(line), ticket, nearby
                             )
    }
    1 -> when (line) {
      "" -> phase + 1 to triple
      "your ticket:" -> phase to triple
      else -> phase to Triple(range, line.split(",").map { it.toInt() }, nearby)
    }
    else -> when (line) {
      "nearby tickets:" -> phase to triple
      else -> phase to Triple(range, ticket, nearby + listOf(line.split(",").map { it.toInt() }))
    }
  }
}.second



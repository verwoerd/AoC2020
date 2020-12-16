package day16.part2

import day16.part1.Ticket
import day16.part1.TicketConstraint
import day16.part1.parseInput
import java.io.BufferedReader

/**
 * @author verwoerd
 * @since 16/12/2020
 */
fun part2(input: BufferedReader): Any {
  val (constraints, ticket, nearbyTickets) = input.parseInput()

  val validTickets = nearbyTickets.filter { it.isValid(constraints) }

  // All labels paired with indexes that can be applied to all tickets
  val startOptions = constraints.map { constraint ->
    constraint.label to ticket.indices.filter { i ->
      validTickets.map { it[i] }.all { value -> value in constraint }
    }
  }

  // solve by assigning unit clauses and propagate
  return generateSequence(Triple(startOptions, mutableMapOf<String, Int>(), setOf<Int>())) { (options, results, seen) ->
    val newSeen = options
      // take all options that have only 1 valid index
      .filter { it.second.size == 1 }
      // assign the result in the results
      .map { (name, indices) ->
        indices.first().also { results[name] = it }
      }.toSet() union seen

    Triple(
      // remove all filled labels
      options.filter { (label, _) -> results[label] == null }
        // remove indices that are assigned
        .map { (name, indices) -> name to indices.filter { it !in seen } }, results, newSeen
          )

  }.first { (options, _, _) -> options.isEmpty() }.second
    .filterKeys { it.startsWith("departure") }
    .values
    .map { ticket[it] }
    .fold(1L, Long::times)

}


fun Ticket.isValid(constraints: List<TicketConstraint>): Boolean =
  all { value -> constraints.any { value in it } }


package day09.part1

import java.io.BufferedReader

/**
 * @author verwoerd
 * @since 09/12/2020
 */
fun part1(input: BufferedReader): Any {
  return doSolution(input)
}

fun doSolution(input: BufferedReader, window: Int = 25): Any {
  return input.lineSequence().map { it.toLong() }.findWeakness(window)
}

fun Sequence<Long>.findWeakness(window: Int = 25) =
  windowed(size = window + 1)
    .first { list ->
      val target = list.last()
      val search = list.dropLast(1).sorted()
      search.takeLastWhile { it >= target / 2 }
        .map { cost ->
          cost to search.takeWhile { it <= (target - cost) }.firstOrNull { it + cost == target }
        }.all { it.second == null }
    }.last()

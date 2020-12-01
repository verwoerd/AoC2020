package day01.part1

import java.io.BufferedReader

import kotlin.streams.toList

/**
 * @author verwoerd
 * @since 01/12/2020
 */
fun part1(input: BufferedReader): Any {
  val expenses = input.lines().map { it.toInt() }.sorted().toList()
  val (left, right) = expenses.takeLastWhile { it >= 1000 }.map { cost ->
    cost to expenses.takeWhile { it >= (2020 - cost) }.firstOrNull { it + cost == 2020 }
  }.first { it.second != null }
  return left * (right ?: error("no Solution"))
}

package day15.part2

import java.io.BufferedReader

/**
 * @author verwoerd
 * @since 15/12/2020
 */

fun part2(input: BufferedReader): Any {
  val memory = mutableMapOf<Int, Int>().withDefault { -1 }
  val spoken = input.readLine().split(",").map { it.toInt() }
  spoken.forEachIndexed { index, number ->
    memory[number] = 1 + index
  }
  return generateSequence(spoken.last() to spoken.size) { (last, turn) ->
    when (val value = memory.getValue(last)) {
      -1 -> 0
      else -> turn - value
    }.also { memory[last] = turn } to turn + 1
  }.takeWhile { it.second <= 30000000 }.last().first
}

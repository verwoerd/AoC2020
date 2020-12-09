package day09.part2

import day09.part1.findWeakness
import java.io.BufferedReader

/**
 * @author verwoerd
 * @since 09/12/2020
 */
fun part2(input: BufferedReader): Any {
  return doPart2(input)
}

fun doSolution(input: BufferedReader, window: Int = 25): Pair<List<Long>, Long> {
  val list = input.lineSequence().map { it.toLong() }.toList()
  return list to list.asSequence().findWeakness(window)
}


fun doPart2(input: BufferedReader, window: Int = 25): Any {
  val (list, target) = doSolution(input, window)
  var start = 0
  var end = 1
  var current = list[start]
  while (current != target) {
    when {
      current > target -> {
        start += 1
        end = start + 1
        current = list[start]
      }
      else -> end++
    }
    current += list[end]
  }
  val min = list.subList(start, end).minOrNull()!!
  val max = list.subList(start, end).maxOrNull()!!
  return min + max
}

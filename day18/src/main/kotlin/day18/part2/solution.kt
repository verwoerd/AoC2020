package day18.part2

import day18.part1.getResult
import day18.part1.tokenize
import java.io.BufferedReader

/**
 * @author verwoerd
 * @since 18/12/2020
 */
fun part2(input: BufferedReader): Any {
  return input.lineSequence()
    .map { tokenize(it) }
    .map { it.doCalculationPart2(0L, 0).first }
    .sum()
}

fun List<String>.doCalculationPart2(leftValue: Long, index: Int): Pair<Long, Int> {
  if (index == size) return leftValue to index
  return when (val current = get(index)) {
    "(" -> doCalculationPart2(0, index + 1).let { doCalculationPart2(it.first, it.second) }
    ")" -> leftValue to index + 1
    "+" -> getResult(index + 1, ::doCalculationPart2).let { doCalculationPart2(leftValue + it.first, it.second) }
    "*" -> doCalculationPart2(0, index + 1).let { it.copy(first = it.first * leftValue) }
    else -> doCalculationPart2(current.toLong(), index + 1)
  }
}



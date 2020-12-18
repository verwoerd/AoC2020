package day18.part1

import java.io.BufferedReader


/**
 * @author verwoerd
 * @since 18/12/2020
 */
fun part1(input: BufferedReader): Any {
  return input.lineSequence()
    .map { tokenize(it) }
    .map { it.doCalculationPart1(0L, 0).first }
    .sum()
}

fun tokenize(line: String): List<String> {
  return line.flatMap {
    when (it) {
      '(' -> listOf('(', ' ')
      ')' -> listOf(' ', ')')
      else -> listOf(it)
    }
  }.joinToString("").split(" ")

}


fun List<String>.doCalculationPart1(leftValue: Long, index: Int): Pair<Long, Int> {
  if (index == size) return leftValue to index
  return when (val current = get(index)) {
    "(" -> doCalculationPart1(0, index + 1).let { doCalculationPart1(it.first, it.second) }
    ")" -> leftValue to index + 1
    "+" -> getResult(index + 1).let { doCalculationPart1(leftValue + it.first, it.second) }
    "*" -> getResult(index + 1).let { doCalculationPart1(leftValue * it.first, it.second) }
    else -> doCalculationPart1(current.toLong(), index + 1)
  }
}

fun List<String>.getResult(
  index: Int,
  function: (Long, Int) -> Pair<Long, Int> = ::doCalculationPart1
                          ): Pair<Long, Int> {
  if (index == size) error("invalid format")
  return when (val current = get(index)) {
    "(" -> function(0L, index + 1)
    else -> current.toLong() to index + 1
  }
}

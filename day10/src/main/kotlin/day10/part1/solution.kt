package day10.part1

import java.io.BufferedReader

/**
 * @author verwoerd
 * @since 10/12/2020
 */
fun part1(input: BufferedReader): Any {
  return input.lineSequence().map { it.toInt() }.sorted().fold(Triple(0, 0, 0)) { (single, triple, last), i ->
    when (i - last) {
      1 -> Triple(single + 1, triple, i)
      3 -> Triple(single, triple + 1, i)
      else -> Triple(single, triple, i)
    }
  }.let { (first, triple) -> first * triple.inc() }
}

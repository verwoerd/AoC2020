package day06.part1

import java.io.BufferedReader

/**
 * @author verwoerd
 * @since 06/12/2020
 */
fun part1(input: BufferedReader): Any {
  return input.lineSequence().fold(mutableSetOf<Char>() to 0) { (seen, count), current ->
    when {
      current.isEmpty() -> mutableSetOf<Char>() to count + seen.size
      else -> seen.also { it.addAll(current.asSequence()) } to count
    }
  }.let { (seen, count) -> count + seen.size }
}

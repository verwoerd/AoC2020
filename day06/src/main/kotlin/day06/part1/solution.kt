package day06.part1

import java.io.BufferedReader

/**
 * @author verwoerd
 * @since 06/12/2020
 */
fun part1(input: BufferedReader): Any {
  return input.lineSequence().fold(emptySet<Char>() to 0) { (seen, count), current ->
    when {
      current.isEmpty() -> emptySet<Char>() to count + seen.size
      else -> seen.union(current.asIterable()) to count
    }
  }.let { (seen, count) -> count + seen.size }
}

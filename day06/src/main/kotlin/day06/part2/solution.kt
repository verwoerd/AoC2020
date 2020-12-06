package day06.part2

import java.io.BufferedReader

/**
 * @author verwoerd
 * @since 06/12/2020
 */
fun part2(input: BufferedReader): Any {
  return input.lineSequence().fold(Triple(mutableSetOf<Char>(), 0, true)) { (seen, count, new), current ->
    when {
      current.isEmpty() -> Triple(mutableSetOf(), count + seen.size, true)
      new -> Triple(seen.also { it.addAll(current.asSequence()) }, count, false)
      else -> Triple(seen.also { set -> set.removeIf { it !in current } }, count, new)
    }
  }.let { (seen, count) -> count + seen.size }
}

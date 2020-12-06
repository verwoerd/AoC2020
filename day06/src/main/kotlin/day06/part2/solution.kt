package day06.part2

import java.io.BufferedReader

/**
 * @author verwoerd
 * @since 06/12/2020
 */
fun part2(input: BufferedReader): Any {
  return input.lineSequence().fold(Triple(emptySet<Char>(), 0, true)) { (seen, count, new), current ->
    when {
      current.isEmpty() -> Triple(emptySet(), count + seen.size, true)
      new -> Triple(current.toSet(), count, false)
      else -> Triple(seen.intersect(current.asIterable()), count, new)
    }
  }.let { (seen, count) -> count + seen.size }
}

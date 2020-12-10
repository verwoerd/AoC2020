package day10.part2

import java.io.BufferedReader

/**
 * @author verwoerd
 * @since 10/12/2020
 */
fun part2(input: BufferedReader): Any {
  val list =
    input.lineSequence().map { it.toLong() }
      .plusElement(0)
      .toMutableSet()
      .also { it.add(it.maxOrNull()!! + 3) }.toSortedSet()

  val cache = mutableMapOf(0L to 1L).withDefault { 0 }
  list.asSequence().forEach { adapter ->
    sequenceOf(adapter + 1, adapter + 2, adapter + 3)
      .filter { it in list }
      .forEach {
        cache[it] = cache.getValue(it) + cache.getValue(adapter)
      }
  }

  return cache.getValue(list.maxOrNull()!!)
}

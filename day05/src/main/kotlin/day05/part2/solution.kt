package day05.part2

import day05.part1.toId
import java.io.BufferedReader

/**
 * @author verwoerd
 * @since 05/12/2020
 */
fun part2(input: BufferedReader): Any {
  return input.lineSequence().map { it.toId() }.sorted()
    .windowed(size = 2, step = 1).first { (lower, upper) -> upper - lower != 1 }.first() + 1
}

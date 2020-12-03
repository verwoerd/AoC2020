package day03.part1

import toInt
import java.io.BufferedReader

/**
 * @author verwoerd
 * @since 03/12/2020
 */
fun part1(input: BufferedReader): Any {
  val lines = input.readLines()
  return lines.foldIndexed(0) { index, trees, value ->
    trees + (value[(index * 3) % value.length] == '#').toInt()
  }
}

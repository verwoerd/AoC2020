package day03.part1

import toInt
import java.io.BufferedReader

/**
 * @author verwoerd
 * @since 03/12/2020
 */
fun part1(input: BufferedReader): Any {
  val lines = input.readLines()
  val width = lines.first().length
  return lines.drop(1).fold(0 to 0) { (x, trees), value ->
    (x + 3).let { it to trees + (value[it.rem(width)] == '#').toInt() }
  }.second
}

package day03.part1

import java.io.BufferedReader

/**
 * @author verwoerd
 * @since 03/12/2020
 */
fun part1(input: BufferedReader): Any {
  val lines = input.readLines()
  val width = lines.first().length
  var x = 0
  return lines.drop(1).count {
    x += 3
    it[x.rem(width)] == '#'
  }
}

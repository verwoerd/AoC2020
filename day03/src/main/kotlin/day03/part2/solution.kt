package day03.part2

import java.io.BufferedReader

/**
 * @author verwoerd
 * @since 03/12/2020
 */
fun part2(input: BufferedReader): Any {
  val lines = input.readLines()
  val one = lines.countTrees(1)
  val three = lines.countTrees(3)
  val five = lines.countTrees(5)
  val seven = lines.countTrees(7)
  val twoDown = lines.countTrees(1, 2)
  return one * three * five * seven * twoDown
}

fun List<String>.countTrees(right: Int, down: Int = 1): Long {
  val width = first().length
  var x = 0
  return drop(down).filterIndexed { index, _ -> index.rem(down) == 0 }.count {
    x += right
    it[x.rem(width)] == '#'
  }.toLong()
}

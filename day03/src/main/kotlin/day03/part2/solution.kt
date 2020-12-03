package day03.part2

import toInt
import java.io.BufferedReader

/**
 * @author verwoerd
 * @since 03/12/2020
 */
fun part2(input: BufferedReader): Any {
  val lines = input.readLines()
  val width = lines.first().length
  val one = lines.countTrees(width, right = 1)
  val three = lines.countTrees(width, right = 3)
  val five = lines.countTrees(width, right = 5)
  val seven = lines.countTrees(width, right = 7)
  val twoDown = lines.countTrees(width, right = 1, down = 2)
  return one * three * five * seven * twoDown
}

fun List<String>.countTrees(width: Int, right: Int, down: Int = 1) =
  drop(down).filterIndexed { index, _ -> index.rem(down) == 0 }.fold(0 to 0L) { (x, trees), value ->
    (x + right).let { it to trees + (value[it.rem(width)] == '#').toInt() }
  }.second

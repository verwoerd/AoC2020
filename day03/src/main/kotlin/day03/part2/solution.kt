package day03.part2

import toInt
import java.io.BufferedReader

/**
 * @author verwoerd
 * @since 03/12/2020
 */
fun part2(input: BufferedReader): Any {
  val lines = input.readLines()
  val one = lines.countTrees(right = 1)
  val three = lines.countTrees(right = 3)
  val five = lines.countTrees(right = 5)
  val seven = lines.countTrees(right = 7)
  val twoDown = lines.countTrees(right = 1, down = 2)
  return one * three * five * seven * twoDown
}

fun List<String>.countTrees(right: Int, down: Int = 1) =
  filterIndexed { index, _ -> index % down == 0 }.foldIndexed(0) { index, trees, value ->
    trees + (value[(index * right) % value.length] == '#').toInt()
  }

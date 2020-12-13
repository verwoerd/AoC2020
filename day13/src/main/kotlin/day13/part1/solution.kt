package day13.part1

import toInt
import java.io.BufferedReader

/**
 * @author verwoerd
 * @since 13/12/2020
 */
fun part1(input: BufferedReader): Any {
  val timestamp = input.readLine().toLong()
  val lines = input.readLine().split(",").filter { it != "x" }.map { it.toLong() }
  val (line, time) = lines.map { it to (timestamp / it + (timestamp % it != 0L).toInt()) * it }
    .minByOrNull { it.second } ?: error("invalid")
  return line * (time - timestamp)
}

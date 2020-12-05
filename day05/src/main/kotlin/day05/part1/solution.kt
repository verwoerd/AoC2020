package day05.part1

import java.io.BufferedReader

/**
 * @author verwoerd
 * @since 05/12/2020
 */
fun part1(input: BufferedReader): Any {
  return input.lineSequence().map { it.toId() }.maxOrNull()!!
}

fun String.toId(): Int {
  return take(7).fold(0) { acc, c ->
    acc shl 1 or when (c) {
      'F' -> 0
      'B' -> 1
      else -> error("Invalid")
    }
  } * 8 + takeLast(3).fold(0) { acc, c ->
    acc shl 1 or when (c) {
      'L' -> 0
      'R' -> 1
      else -> error("Invalid")
    }
  }
}



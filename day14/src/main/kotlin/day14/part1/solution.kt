package day14.part1

import java.io.BufferedReader

/**
 * @author verwoerd
 * @since 14/12/2020
 */
fun part1(input: BufferedReader): Any {
  val memory = mutableMapOf<Long, Long>()
  input.readLines().fold("") { bitmask, line ->
    when {
      line.startsWith("mask") -> line.drop(7)
      else -> bitmask.also {
        val (address, value) = line.split(" = ")
        memory[address.drop(4).dropLast(1).toLong()] = value.toLong().applyBitMask(bitmask)
      }
    }
  }
  return memory.map { it.value }.sum()
}

fun Long.applyBitMask(mask: String): Long {
  return mask.foldIndexed(0L) { index, acc, c ->
    (acc shl 1) or when (c) {
      '0' -> 0L
      '1' -> 1L
      else -> (this shr (mask.length - index - 1)) and 1L
    }
  }
}

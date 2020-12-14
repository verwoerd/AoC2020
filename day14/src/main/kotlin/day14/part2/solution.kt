package day14.part2

import java.io.BufferedReader

/**
 * @author verwoerd
 * @since 14/12/2020
 */
fun part2(input: BufferedReader): Any {
  val memory = mutableMapOf<Long, Long>()
  input.readLines().fold("") { bitmask, line ->
    when {
      line.startsWith("mask") -> line.drop(7)
      else -> bitmask.also {
        val (address, rawValue) = line.split(" = ")
        val value = rawValue.toLong()
        address.drop(4).dropLast(1).toLong().generateBitMask(bitmask).forEach { memory[it] = value }
      }
    }
  }
  return memory.map { it.value }.sum()
}

fun Long.generateBitMask(mask: String) = generateBitMaks(mask, mask.length - 1, 0)

fun Long.generateBitMaks(mask: String, index: Int, current: Long): List<Long> {
  if (index == -1) return listOf(current)
  val next = (current shl 1)
  return when (mask[mask.length - 1 - index]) {
    '0' -> generateBitMaks(mask, index - 1, next or ((this shr index) and 1L))
    '1' -> generateBitMaks(mask, index - 1, next or 1L)
    'X' -> listOf(0L, 1L).flatMap { generateBitMaks(mask, index - 1, next or it) }
    else -> error("invalid")
  }
}

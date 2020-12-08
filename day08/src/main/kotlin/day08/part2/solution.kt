package day08.part2

import day08.part1.Instruction
import day08.part1.Operation
import java.io.BufferedReader

/**
 * @author verwoerd
 * @since 08/12/2020
 */
fun part2(input: BufferedReader): Any {
  val code = input.readLines().map { Instruction.parseLine(it) }
  return code.indices.asSequence().filter { code[it].operation != Operation.ACC }
    .filter { code[it].operation != Operation.NOP && code[it].operand != 0 }
    .map { code.isBruteForce(it) }
    .first { it.first }.second
}

fun List<Instruction>.isBruteForce(switch: Int): Pair<Boolean, Int> {
  val seen = mutableSetOf<Int>()
  val (acc, pc) = generateSequence(0 to 0) { (acc, pc) ->
    when (pc) {
      switch -> get(pc).flip()
      else -> get(pc)
    }.execute(acc, pc)
  }.first { (_, pc) -> !seen.add(pc) || pc == size }
  return (pc == size) to acc
}

fun Instruction.flip() = when (operation) {
  Operation.NOP -> this.copy(operation = Operation.JMP)
  Operation.JMP -> this.copy(operation = Operation.NOP)
  else -> error("Invalid flip")
}


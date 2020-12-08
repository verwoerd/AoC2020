package day08.part1

import day08.part1.Instruction.Companion.parseLine
import java.io.BufferedReader

/**
 * @author verwoerd
 * @since 08/12/2020
 */
fun part1(input: BufferedReader): Any {
  val code = input.readLines().map { parseLine(it) }
  val seen = mutableSetOf<Int>()
  return generateSequence(0 to 0) { (acc, pc) ->
    code[pc].execute(acc, pc)
  }.first { (_, pc) -> !seen.add(pc) }.first
}

enum class Operation {
  ACC, JMP, NOP;
}

data class Instruction(
  val operation: Operation,
  val operand: Int = 0
                      ) {
  companion object {
    fun parseLine(line: String) = line.split(" ").chunked(2).first().let { (operation, value) ->
      Instruction(Operation.valueOf(operation.toUpperCase()), value.toInt())
    }
  }

  fun execute(acc: Int, pc: Int) = when (operation) {
    Operation.ACC -> acc + operand to pc + 1
    Operation.JMP -> acc to pc + operand
    Operation.NOP -> acc to pc + 1
  }
}

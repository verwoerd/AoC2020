package day25.part1

import java.io.BufferedReader
import java.math.BigInteger
import java.math.BigInteger.ONE

/**
 * @author verwoerd
 * @since 25/12/20
 */
fun part1(input: BufferedReader): Any {
  return input.lineSequence().take(2).map { it.toBigInteger() }
    .reduce { card, room -> room.modPow(card.findLoop(), MOD) }
}

val SEVEN: BigInteger = BigInteger.valueOf(7L)
val MOD: BigInteger = BigInteger.valueOf(20201227L)

private fun BigInteger.findLoop() =
  generateSequence(ONE) { (SEVEN * it) % MOD }.indexOfFirst { it == this }.toBigInteger()




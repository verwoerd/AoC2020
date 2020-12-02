package day02.part2

import day02.part1.regex
import java.io.BufferedReader

/**
 * @author verwoerd
 * @since 02/12/2020
 */
fun part2(input: BufferedReader): Any {
  return input.lineSequence().map { regex.matchEntire(it)!!.groupValues.drop(1) }
    .count { (min, max, value, password) -> validPassword(min.toInt(), max.toInt(), value.first(), password) }
}

fun validPassword(start: Int, end: Int, value: Char, password: CharSequence) =
  (password.drop(start - 1).first() == value) xor (password.drop(end - 1).first() == value)


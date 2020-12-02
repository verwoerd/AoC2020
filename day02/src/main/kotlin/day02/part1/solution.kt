package day02.part1

import java.io.BufferedReader

/**
 * @author verwoerd
 * @since 02/12/2020
 */
fun part1(input: BufferedReader): Any {
  return input.lineSequence().map { regex.matchEntire(it)!!.groupValues.drop(1) }
    .count { (min, max, value, password) -> validPassword(min.toInt(), max.toInt(), value.first(), password) }
}


val regex = Regex("(?<min>\\d+)-(?<max>\\d+) (?<char>\\w): (?<password>\\w+)")

fun validPassword(min: Int, max: Int, value: Char, password: CharSequence) =
  password.count { it == value } in min..max

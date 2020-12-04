package day04.part2

import day04.part1.Passport
import day04.part1.parseLines
import java.io.BufferedReader

/**
 * @author verwoerd
 * @since 04/12/2020
 */
fun part2(input: BufferedReader): Any {
  return parseLines(input).count { it.extraValidation() }
}

val colorRegex = Regex("^#[\\w\\d]{6}$")
val pidRegex = Regex("^\\d{9}$")
val eyeColours = setOf("amb", "blu", "brn", "gry", "grn", "hzl", "oth")

fun Passport.extraValidation() = isValid() && byr.toIntOrNull() ?: 0 in 1920..2002 &&
    iyr.toIntOrNull() ?: 0 in 2010..2020 &&
    eyr.toIntOrNull() ?: 0 in 2020..2030 &&
    when (hgt.takeLast(2)) {
      "cm" -> hgt.dropLast(2).toIntOrNull() ?: 0 in 150..193
      "in" -> hgt.dropLast(2).toIntOrNull() ?: 0 in 59..76
      else -> false
    } && hcl.matches(colorRegex)
    && ecl in eyeColours
    && pid.matches(pidRegex)

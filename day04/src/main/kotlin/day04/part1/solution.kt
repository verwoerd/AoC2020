package day04.part1

import day04.part1.Passport.Companion.readPassport
import java.io.BufferedReader

/**
 * @author verwoerd
 * @since 04/12/2020
 */
fun part1(input: BufferedReader): Any {
  return parseLines(input).count { it.isValid() }
}

fun parseLines(input: BufferedReader) = input.lineSequence()
  .runningFold(Passport()) { current, line ->
    when {
      line.isBlank() -> Passport()
      current.isValid() -> Passport() // corner case where cid is on a separate line, but passport is already valid
      else -> readPassport(line,current)
    }
  }

data class Passport(
  val byr: String = "",
  val iyr: String = "",
  val eyr: String = "",
  val hgt: String = "",
  val hcl: String = "",
  val ecl: String = "",
  val pid: String = "",
  val cid: String = ""
                   ) {
  companion object {
    fun readPassport(line: String, start: Passport= Passport()): Passport =
      line.split(" ").map { it.split(":").take(2) }.fold(start) { passport, (name, value) ->
        when (name) {
          "byr" -> passport.copy(byr = value)
          "iyr" -> passport.copy(iyr = value)
          "eyr" -> passport.copy(eyr = value)
          "hgt" -> passport.copy(hgt = value)
          "hcl" -> passport.copy(hcl = value)
          "ecl" -> passport.copy(ecl = value)
          "pid" -> passport.copy(pid = value)
          "cid" -> passport.copy(cid = value)
          else -> error("invalid property")
        }
      }
  }

  fun isValid() = byr.isNotBlank() &&
      iyr.isNotBlank() &&
      eyr.isNotBlank() &&
      hgt.isNotBlank() &&
      hcl.isNotBlank() &&
      ecl.isNotBlank() &&
      pid.isNotBlank()
}

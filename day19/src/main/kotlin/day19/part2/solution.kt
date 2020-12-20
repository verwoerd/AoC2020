package day19.part2

import day19.part1.cache
import day19.part1.calculate
import day19.part1.readInput
import java.io.BufferedReader

/**
 * @author verwoerd
 * @since 19/12/2020
 */
fun part2(input: BufferedReader): Any {
  val (ruleList, lines) = input.readInput()
  val rules = ruleList.toMutableMap()
  // This is a one or more.
  rules[8] = "42 +"
  // just generate combinations of expanding palindromes of rule 42 31
  // faster then figuring out if this is even possible in proper regex
  rules[11] = generateSequence("42 31") {
    it.substring(0, it.length / 2) + " 42 31 " + it.substring(it.length / 2 + 1)
  }.take(4).joinToString(" | ")
  // note: The take number has be tweaked with trial and error, if it answer stops increasing I found the sweet spot
  // for my input
  cache.clear()
  val matchRule = Regex(rules.calculate(0))
  return lines.count { it.matches(matchRule) }
}


/*
 * Copyright (c) 2020 Keystone Strategic b.v.
 *
 * All rights reserved.
 */

package day19.part1

import java.io.BufferedReader
import java.util.concurrent.ConcurrentHashMap

/**
 * @author verwoerd
 * @since 19/12/2020
 */
fun part1(input: BufferedReader): Any {
  val (rules, lines) = input.readInput()
  val matchRule = Regex(rules.calculate(0))
  return lines.count { it.matches(matchRule) }
}

fun BufferedReader.readInput(): Pair<Map<Int, String>, List<String>> {
  val (_, ruleList, lines) = lineSequence()
    .fold(Triple(0, emptyList<Pair<Int, String>>(), emptyList<String>())) { (phase, rules, lines), line ->
      when {
        // switch to second part of the input
        line.isBlank() -> Triple(1, rules, lines)
        // just add the lines to the lines list
        phase == 1 -> Triple(phase, rules, lines + line)
        else -> {
          // make pairs with name and the appropriate rule
          val (name, rule) = line.split(": ")
          Triple(phase, rules + (name.toInt() to rule), lines)
        }
      }
    }
  return ruleList.toMap() to lines
}

val cache = ConcurrentHashMap<Int, String>()

fun Map<Int, String>.calculate(key: Int): String {
  if (cache[key] == null) {
    val entry = get(key) ?: error("Unknown rule: $key")
    cache[key] = when {
      entry.startsWith("\"") -> entry.drop(1).take(1)
      else -> entry.split(" ").joinToString("") {
        // build a regex string
        when (it) {
          "|" -> "|"
          "+" -> "+" // only for part 2
          "" -> ""
          else -> "(${calculate(it.toInt())})"
        }
      }
    }
  }
  return cache[key]!!
}

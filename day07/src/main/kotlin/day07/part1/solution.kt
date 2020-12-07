package day07.part1

import java.io.BufferedReader

/**
 * @author verwoerd
 * @since 07/12/2020
 */
fun part1(input: BufferedReader): Any {
  val bags =
    input.lineSequence().map(Bag.Companion::parseLine).fold(mutableMapOf<String, MutableSet<String>>()) { acc, bag ->
      bag.contents.map { acc.getOrPut(it.key, { mutableSetOf() }).add(bag.color) }.let { acc }
    }.withDefault { mutableSetOf() }
  return bags.getParents("shiny gold").count()
}

fun Map<String,MutableSet<String>>.getParents(current: String, seen: Set<String> = emptySet()): Set<String> =
  seen union getValue(current).filter { it !in seen }.flatMap { getParents(it, seen + it) }

val baseRegex = Regex("^(?<color>[\\w ]+) bags contain (?<contents>.+)\\.$")
val contentRegex = Regex(" ?(?<count>\\d+) (?<color>[\\w ]+)")
val splitRegex = Regex(" bags?,?")

data class Bag(
  val color: String,
  val contents: Map<String, Int>
              ) {
  companion object {
    fun parseLine(line: String): Bag {
      val (color, contents) = baseRegex.matchEntire(line)!!.destructured
      return Bag(
        color = color, contents = when (contents) {
          "no other bags" -> emptyMap()
          else -> contents.split(splitRegex).filter { it.isNotEmpty() }
            .map { contentRegex.matchEntire(it)!!.groupValues.drop(1) }
            .map { (count, color) -> color to count.toInt() }.toMap()
        }
                )
    }
  }
}

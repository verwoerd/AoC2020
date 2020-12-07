package day07.part1

import java.io.BufferedReader
import java.util.LinkedList

/**
 * @author verwoerd
 * @since 07/12/2020
 */
fun part1(input: BufferedReader): Any {
  val bags =
    input.lineSequence().map(Bag.Companion::parseLine).fold(mutableMapOf<String, MutableSet<String>>()) { acc, bag ->
      bag.contents.map { acc.getOrPut(it.key, { mutableSetOf() }).add(bag.color) }.let { acc }
    }.withDefault { mutableSetOf() }

  val queue = LinkedList<String>().also { it.add("shiny gold") }
  val seen = mutableSetOf<String>()
  while (queue.isNotEmpty()) {
    val current = queue.pop()
    bags.getValue(current).filter { seen.add(it) }.toCollection(queue)
  }
  return seen.size
}

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


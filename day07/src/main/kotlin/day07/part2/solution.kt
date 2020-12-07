package day07.part2

import day07.part1.Bag
import java.io.BufferedReader

/**
 * @author verwoerd
 * @since 07/12/2020
 */
fun part2(input: BufferedReader): Any {
  return input.lineSequence().map(Bag.Companion::parseLine).associateBy { it.color }.findCount("shiny gold") - 1
}

fun Map<String, Bag>.findCount(color: String): Int =
  get(color)!!.contents.takeIf { it.isNotEmpty() }?.map { (key, value) -> value * findCount(key) }?.sum()?.inc() ?: 1


package day01.part2

import java.io.BufferedReader
import kotlin.streams.toList

/**
 * @author verwoerd
 * @since 01/12/2020
 */
fun part2(input: BufferedReader): Any {
  val expenses = input.lines().map { it.toInt() }.sorted().toList()
  val minValue = expenses.first()
  val(left,middle, right) = expenses.takeLastWhile { it >= 674 }.map { cost ->
    val value = expenses.takeWhile { it <= (2020 - cost - minValue) }.map { second ->
      second to expenses.takeWhile { it <= (2020-cost - second)  }.firstOrNull { it + cost + second == 2020 }
    }.firstOrNull { it.second != null }
    Triple(cost,value?.first,value?.second)
  }.first { it.third != null }
  return left*(right ?: error("no Solution"))*(middle ?: error("no Solution"))
}

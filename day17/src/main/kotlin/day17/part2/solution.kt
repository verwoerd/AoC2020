package day17.part2

import QuartetCoordinate
import adjacentCircularCoordinates
import expand
import range
import java.io.BufferedReader

/**
 * @author verwoerd
 * @since 17/12/2020
 */
fun part2(input: BufferedReader): Any {
  var coordinates = input.lineSequence().flatMapIndexed { y, line ->
    line.mapIndexedNotNull { x, c ->
      when (c) {
        '#' -> QuartetCoordinate(x, y, z = 0, w = 0)
        else -> null
      }
    }
  }.toSet()
  repeat(6) {
    coordinates = coordinates.executeProcedure()
  }
  return coordinates.size
}

fun Set<QuartetCoordinate>.executeProcedure(): Set<QuartetCoordinate> {
  val range = range()
  return range.wRange.expand().flatMap { w ->
    range.zRange.expand().flatMap { z ->
      range.yRange.expand().flatMap { y ->
        range.xRange.expand().mapNotNull { x ->
          val coordinate = QuartetCoordinate(x, y, z, w)
          val active = contains(coordinate)
          val activeNeighbours = adjacentCircularCoordinates(coordinate).count { contains(it) }
          when {
            active && activeNeighbours in 2..3 -> coordinate
            !active && activeNeighbours == 3 -> coordinate
            else -> null
          }
        }
      }
    }
  }.toSet()
}

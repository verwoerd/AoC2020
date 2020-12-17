package day17.part1

import TripleCoordinate
import adjacentCircularCoordinates
import expand
import range
import java.io.BufferedReader

/**
 * @author verwoerd
 * @since 17/12/2020
 */
fun part1(input: BufferedReader): Any {
  var coordinates = input.lineSequence().flatMapIndexed { y, line ->
    line.mapIndexedNotNull { x, c ->
      when (c) {
        '#' -> TripleCoordinate(x, y, z = 0)
        else -> null
      }
    }
  }.toSet()
  repeat(6) {
    coordinates = coordinates.executeProcedure()
//    println(coordinates.toPrintableString())
  }

  return coordinates.size
}

private fun Set<TripleCoordinate>.toPrintableString(): String {
  val range = range()
  return range.zRange.joinToString("\n") { z ->
    "z= $z\n" + range.yRange.joinToString("\n") { y ->
      range.xRange.joinToString("") { x ->
        when (contains(TripleCoordinate(x, y, z))) {
          true -> "#"
          else -> "."
        }
      }
    }
  }
}

fun Set<TripleCoordinate>.executeProcedure(): Set<TripleCoordinate> {
  val range = range()
  return range.zRange.expand().flatMap { z ->
    range.yRange.expand().flatMap { y ->
      range.xRange.expand().mapNotNull { x ->
        val coordinate = TripleCoordinate(x, y, z)
        val active = contains(coordinate)
        val activeNeighbours = adjacentCircularCoordinates(coordinate).count { it in this }
        when {
          active && activeNeighbours in 2..3 -> coordinate
          !active && activeNeighbours == 3 -> coordinate
          else -> null
        }
      }
    }
  }.toSet()
}

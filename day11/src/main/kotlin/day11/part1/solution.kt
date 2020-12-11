package day11.part1

import Coordinate
import CoordinateRange
import adjacentCircularCoordinates
import asRange
import range
import xRange
import yRange
import java.io.BufferedReader

/**
 * @author verwoerd
 * @since 11/12/2020
 */
fun part1(input: BufferedReader): Any {
  val start = readInput(input)
  val range = start.range()
  return generateSequence(start to start.doChairDance(range)) { (_, current) -> current to current.doChairDance(range) }
    .first { (previous, current) -> previous == current }
    .second.countOccupiedChairs()
}

fun readInput(input: BufferedReader) = input.lineSequence().flatMapIndexed { y, line ->
  line.mapIndexed { x, char ->
    Coordinate(x, y) to char
  }
}.toMap()

fun Map<Coordinate, Char>.doChairDance(range: CoordinateRange): Map<Coordinate, Char> {
  return range.yRange.flatMap { y ->
    range.xRange.map { x ->
      Coordinate(x, y) to when {
        get(Coordinate(x, y)) == '.' -> '.'
        else -> adjacentCircularCoordinates(Coordinate(x, y))
          .filter { it in range }
          .map { getValue(it) }.count { it == '#' }.let {
            when (it) {
              0 -> '#'
              in 1..3 -> getValue(Coordinate(x, y))
              else -> 'L'
            }
          }
      }
    }
  }.toMap()
}

fun Map<Coordinate, Char>.countOccupiedChairs() = yRange().asRange().sumBy { y ->
  xRange().asRange().count { x ->
    getValue(Coordinate(x, y)) == '#'
  }
}

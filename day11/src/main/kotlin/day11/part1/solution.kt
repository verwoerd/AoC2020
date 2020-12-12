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
  // For all y values in the current map
  return range.yRange.flatMap { y ->
    // For all x values in the current map
    range.xRange.map { x ->
      // Construct the coordinate key for the current coordinate
      Coordinate(x, y)
        .let { current ->
          // create a pair based of the coordinate and the new value based on the adjacent seat rules
          current to
              when (val content = getValue(current)) {
                '.' -> '.' // spots with no seats don't need ot be considered
                else -> adjacentCircularCoordinates(current)  // get all seats adjacent to the current one
                  .filter { it in range } // only consider seats in range of the current grid
                  .map { getValue(it) } // get the current value of the adjacent seat
                  .count { it == '#' } // count the number of seats that are occupied
                  .let {
                    when (it) {
                      0 -> '#'    // if no one is sitting around, someone will sit down
                      in 1..3 -> content // if less then 4 people around, don't change the state
                      else -> 'L' // 4 or more, the seat will be empty
                    }
                  }
              }
        }
    }
    // And put it together in a map
  }.toMap()
}

fun Map<Coordinate, Char>.countOccupiedChairs() = yRange().asRange().sumBy { y ->
  xRange().asRange().count { x ->
    getValue(Coordinate(x, y)) == '#'
  }
}

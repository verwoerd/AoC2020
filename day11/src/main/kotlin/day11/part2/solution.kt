package day11.part2

import Coordinate
import CoordinateRange
import adjacentCircularCoordinates
import day11.part1.readInput
import origin
import range
import java.io.BufferedReader

/**
 * @author verwoerd
 * @since 11/12/2020
 */
fun part2(input: BufferedReader): Any {
  val start = readInput(input)
  val range = start.range()
  val mapSeat = start.filter { it.value == 'L' }
    .map { it.key to start.findAdjacentSeats(it.key, range) }.toMap()
  return generateSequence(start to start.doAdvancedChairDance(mapSeat)) { (_, current) ->
    current to current.doAdvancedChairDance(mapSeat)
  }.first { (previous, current) -> previous == current }.first.countOccupiedChairsAdvanced(mapSeat)
}

fun Map<Coordinate, Char>.findAdjacentSeats(coordinate: Coordinate, range: CoordinateRange): List<Coordinate> =
  adjacentCircularCoordinates(origin).mapNotNull { searchDirection(coordinate, it, range) }.toList()


fun Map<Coordinate, Char>.searchDirection(
  start: Coordinate,
  step: Coordinate,
  range: CoordinateRange
                                         ): Coordinate? = when (val next = start + step) {
  !in range -> null
  else -> generateSequence(next) { it + step }
    .takeWhile { it in range }
    .firstOrNull { getValue(it) == 'L' }
}


fun Map<Coordinate, Char>.doAdvancedChairDance(mapSeat: Map<Coordinate, List<Coordinate>>): Map<Coordinate, Char> {
  return mapSeat.map { (coordinate, adjacent) ->
    coordinate to adjacent.map { getValue(it) }.count { it == '#' }.let {
      when (it) {
        0 -> '#'
        in 1..4 -> getValue(coordinate)
        else -> 'L'
      }
    }
  }.toMap()
}

fun Map<Coordinate, Char>.countOccupiedChairsAdvanced(mapSeat: Map<Coordinate, List<Coordinate>>): Int {
  return mapSeat.keys.count { getValue(it) == '#' }
}

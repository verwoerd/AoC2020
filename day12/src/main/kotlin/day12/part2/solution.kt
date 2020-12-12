package day12.part2

import Coordinate
import manhattanDistance
import origin
import java.io.BufferedReader

/**
 * @author verwoerd
 * @since 12/12/2020
 */
fun part2(input: BufferedReader): Any {
  val waypoint = Coordinate(10, 1)
  return input.lineSequence().map { it.first() to it.drop(1).toInt() }
    .fold((origin to waypoint)) { (location, waypoint), (move, number) ->
      when (move) {
        'N' -> location to waypoint.plusY(number)
        'S' -> location to waypoint.plusY(-number)
        'E' -> location to waypoint.plusX(number)
        'W' -> location to waypoint.plusX(-number)
        'L' -> location to waypoint.left(number)
        'R' -> location to waypoint.right(number)
        'F' -> location + (waypoint * number) to waypoint
        else -> error("Invalid direction")
      }
    }.let { manhattanDistance(origin, it.first) }
}

fun Coordinate.left(degrees: Int) = when (degrees) {
  0 -> this
  90 -> Coordinate(x = -y, y = x)
  180 -> Coordinate(x = -x, y = -y)
  270 -> Coordinate(x = y, y = -x)
  else -> error("invalid")
}

fun Coordinate.right(degrees: Int) = when (degrees) {
  0 -> this
  90 -> Coordinate(x = y, y = -x)
  180 -> Coordinate(x = -x, y = -y)
  270 -> Coordinate(x = -y, y = x)
  else -> error("invalid")
}

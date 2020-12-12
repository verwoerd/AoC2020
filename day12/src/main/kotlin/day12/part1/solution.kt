package day12.part1

import Coordinate
import manhattanDistance
import origin
import java.io.BufferedReader

/**
 * @author verwoerd
 * @since 12/12/2020
 */
fun part1(input: BufferedReader): Any {
  return input.lineSequence().map { it.first() to it.drop(1).toInt() }
    .fold(origin to Direction.EAST) { (location, direction), (move, number) ->
      when (move) {
        'N' -> location.plusY(number) to direction
        'S' -> location.plusY(-number) to direction
        'E' -> location.plusX(number) to direction
        'W' -> location.plusX(-number) to direction
        'L' -> location to direction.left(number)
        'R' -> location to direction.right(number)
        'F' -> direction.forward(number, location) to direction
        else -> error("Invalid direction")
      }
    }.let { manhattanDistance(origin, it.first) }
}

enum class Direction {
  NORTH, WEST, SOUTH, EAST;

  fun left(degrees: Int) = when (this) {
    NORTH -> when (degrees) {
      0 -> NORTH
      90 -> WEST
      180 -> SOUTH
      270 -> EAST
      else -> error("invalid")
    }
    SOUTH -> when (degrees) {
      180 -> NORTH
      270 -> WEST
      0 -> SOUTH
      90 -> EAST
      else -> error("invalid")
    }
    EAST -> when (degrees) {
      90 -> NORTH
      0 -> EAST
      270 -> SOUTH
      180 -> WEST
      else -> error("invalid")
    }
    WEST -> when (degrees) {
      270 -> NORTH
      180 -> EAST
      90 -> SOUTH
      0 -> WEST
      else -> error("invalid")
    }
  }

  fun right(degrees: Int) = when (this) {
    NORTH -> when (degrees) {
      0 -> NORTH
      90 -> EAST
      180 -> SOUTH
      270 -> WEST
      else -> error("invalid")
    }
    SOUTH -> when (degrees) {
      180 -> NORTH
      270 -> EAST
      0 -> SOUTH
      90 -> WEST
      else -> error("invalid")
    }
    EAST -> when (degrees) {
      270 -> NORTH
      180 -> WEST
      90 -> SOUTH
      0 -> EAST
      else -> error("invalid")
    }
    WEST -> when (degrees) {
      90 -> NORTH
      0 -> WEST
      270 -> SOUTH
      180 -> EAST
      else -> error("invalid")
    }
  }

  fun forward(steps: Int, coordinate: Coordinate) = when (this) {
    NORTH -> coordinate.plusY(steps)
    SOUTH -> coordinate.plusY(-steps)
    EAST -> coordinate.plusX(steps)
    WEST -> coordinate.plusX(-steps)
  }
}


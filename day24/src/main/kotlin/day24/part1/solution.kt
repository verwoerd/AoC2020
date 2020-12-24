package day24.part1

import Coordinate
import origin
import java.io.BufferedReader

/**
 * @author verwoerd
 * @since 24/12/2020
 */
fun part1(input: BufferedReader): Any {
  return input.readTileMap()
    .countBlackTiles()
}

fun Int.isBlackTile() = this % 2 == 1

fun <V> Map<Coordinate, List<V>>.countBlackTiles() = count { it.value.size.isBlackTile() }

fun BufferedReader.readTileMap() = lineSequence().map { string ->
  string.fold(origin to "") { (location, prefix), current ->
    when (current) {
      'n', 's' -> location to "$current"
      else -> (location + getHexDirection(prefix + current).diff) to ""
    }
  }.first
}.groupBy { it }

fun getHexDirection(direction: String) = when (direction) {
  "ne" -> HexDirection.NORTH_EAST
  "e" -> HexDirection.EAST
  "se" -> HexDirection.SOUTH_EAST
  "sw" -> HexDirection.SOUTH_WEST
  "w" -> HexDirection.WEST
  "nw" -> HexDirection.NORTH_WEST
  else -> error("Invalid direction $direction")
}

enum class HexDirection(val diff: Coordinate) {
  NORTH_EAST(Coordinate(1, 1)),
  EAST(Coordinate(2, 0)),
  SOUTH_EAST(Coordinate(1, -1)),
  SOUTH_WEST(Coordinate(-1, -1)),
  WEST(Coordinate(-2, 0)),
  NORTH_WEST(Coordinate(-1, 1));
}


package day24.part2

import Coordinate
import day24.part1.HexDirection
import day24.part1.isBlackTile
import day24.part1.readTileMap
import java.io.BufferedReader

/**
 * @author verwoerd
 * @since 24/12/2020
 */
fun part2(input: BufferedReader): Any {
  // Only store the black tiles in a set
  val tiles = input.readTileMap().filter { it.value.size.isBlackTile() }.map { it.key }.toSet()
  return generateSequence(tiles to 0) { (blackTiles, i) ->
    blackTiles.createNewArt() to i + 1
  }.first { it.second == 100 }.first.size
}


fun Coordinate.adjacentHexTiles() = HexDirection.values().map { this + it.diff }

fun Set<Coordinate>.createNewArt() =
  // Only consider directly adjacent tiles
  flatMap { it.adjacentHexTiles() + it }.distinct()
    // gather all the black tiles of this day
    .mapNotNull { coordinate ->
      val neighbours = coordinate.adjacentHexTiles().count { it in this }
      when (coordinate in this) {
        true -> when (neighbours) { // tile is black
          // Any black tile with zero or *more* than 2 black tiles immediately adjacent to it is flipped to white.
          1, 2 -> coordinate
          else -> null
        }
        else -> when (neighbours) {
          //Any white tile with exactly 2 black tiles immediately adjacent to it is flipped to black.
          2 -> coordinate
          else -> null
        }
      }
    }.toSet()

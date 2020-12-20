package day20.part2

import day20.part1.Image
import day20.part1.TILE
import day20.part1.readData
import day20.part1.reconstructImage
import java.io.BufferedReader

/**
 * @author verwoerd
 * @since 20/12/2020
 */
fun part2(input: BufferedReader): Any {
  val images = input.readData()
  val dimension = images.first().data.size

  val reconstructed = images.reconstructImage()
    .clearBorders(dimension).alignImage()
  val seaMonsters = reconstructed.sumBy { it.second }
  val (image) = reconstructed.maxByOrNull { it.second }!!
  val totalTiles = image.data.sumBy { line -> line.count { it == TILE } }
  return totalTiles - (seaMonsters * 15)
}

fun Image.clearBorders(dimension: Int): Image = copy(data = data.mapIndexedNotNull { index, line ->
  when (index % dimension) {
    0, dimension - 1 -> null
    else -> line.mapIndexedNotNull { i, it ->
      when (i % dimension) {
        0, dimension - 1 -> null
        else -> it
      }
    }.joinToString("")
  }
}, combination = emptyList())


fun Image.alignImage(): MutableSet<Pair<Image, Int>> {
  val combos = mutableSetOf<Pair<Image, Int>>()
  var current = this
  repeat(4) {
    current.countSeaMonsters().takeIf { it > 0 }?.also { combos.add(current to it) }
    current = current.rotateLeft()
  }
  current = current.flipVertical()
  repeat(4) {
    current.countSeaMonsters().takeIf { it > 0 }?.also { combos.add(current to it) }
    current = current.rotateLeft()
  }
  current = this.flipVertical().flipHorizontal()
  repeat(4) {
    current.countSeaMonsters().takeIf { it > 0 }?.also { combos.add(current to it) }
    current = current.rotateLeft()
  }
  current = this.flipVertical().flipHorizontal()
  repeat(4) {
    current.countSeaMonsters().takeIf { it > 0 }?.also { combos.add(current to it) }
    current = current.rotateLeft()
  }
  return combos
}

fun Image.countSeaMonsters(): Int {
  return data.windowed(3).sumBy { (line1, line2, line3) ->
    (0..line1.length - 20).count { index ->
      line1Regex.matches(line1.subSequence(index, index + 20)) &&
          line2Regex.matches(line2.subSequence(index, index + 20)) &&
          line3Regex.matches(line3.subSequence(index, index + 20))
    }
  }
}

val line1Regex = Regex("..................#.")
val line2Regex = Regex("#....##....##....###")
val line3Regex = Regex(".#..#..#..#..#..#...")
//                  #
//#    ##    ##    ###
// #  #  #  #  #  #

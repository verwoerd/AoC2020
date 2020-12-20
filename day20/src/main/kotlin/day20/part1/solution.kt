package day20.part1

import java.io.BufferedReader
import kotlin.math.sqrt

/**
 * Not my best work, but went from memory overflow, to correct answer in 15 minutes to 1.5 seconds. I count that as a win.
 *
 * @author verwoerd
 * @since 20/12/2020
 */


fun part1(input: BufferedReader): Any {
  val images = input.readData()
  return images.reconstructImage().cornerProduct()
}

val cache = mutableMapOf<Pair<List<List<Long>>, List<List<Long>>>, List<Image>>()

fun BufferedReader.readData() =
  lineSequence().fold(Triple(emptyList<Image>(), 0L, emptyList<CharSequence>())) { (list, id, image), s ->
    when {
      s.startsWith("Tile ") -> Triple(list, s.drop(5).dropLast(1).toLong(), image)
      s.isBlank() -> Triple(list + Image(listOf(id), image, listOf(listOf(id))), 0L, emptyList())
      else -> Triple(list, id, image + s)
    }
  }.let { (list, id, data) -> list + Image(listOf(id), data, listOf(listOf(id))) }.toMutableSet()

fun MutableSet<Image>.reconstructImage(): Image {
  // If an series of images is of size maxCombi, since the end picture is square
  val maxCombi = sqrt(size.toDouble()).toInt()
  val lookupMap = map { it.id to it }.toMap().toMutableMap()
  // group images by the number of tiles visible on the different sides.
  val dimensionWidthMap = flatMap { listOf(it.left to it, it.right to it, it.top to it, it.bottom to it) }
    .groupBy({ it.first }) { it.second }.toMutableMap()
    .withDefault { emptyList() }

  val lines = mutableSetOf<Image>()
  val checked = mutableSetOf<Pair<Image, Image>>()
  // we will expand combination of images by 1 each step
  var iteration = 1
  while (iteration <= maxCombi) {
    val current = dimensionWidthMap
      .flatMap { entry ->
        // for every image which shares at least one side
        entry.value
          // only look at the images combined
          .filter { it.idList.size == iteration }
          .flatMap { image ->
            entry.value.asSequence()
              // only try to add non combined images
              .filter { it.idList.size == 1 }
              // don't match with yourself
              .filter { it != image }
              // don't do double work
              .filter { checked.add(image to it) && checked.add(it to image) }
              // don't use images twice
              .filter { it.idList.intersect(image.idList).isEmpty() }
              // see if the images fit together on the left and right side
              .flatMap { image.matchWidth(it) }
          }
      }
    // seperate finished lines of max widht from smaller parts that need more
    val (done, new) = current.filter { add(it) }.partition { it.idList.size == maxCombi }
    lines.addAll(done.distinctBy { it.combination }
                   .filter { line -> lines.all { it.combination != line.combination } })
    // put the new combinations in the buckets.
    new.filter { it.id !in lookupMap }.forEach {
      lookupMap[it.id] = it
      dimensionWidthMap[it.left] = dimensionWidthMap.getValue(it.left) + it
      dimensionWidthMap[it.right] = dimensionWidthMap.getValue(it.right) + it
    }
    // increase iteration
    iteration++
    if (new.isEmpty()) break
  }

  // Lines contains all the rows. merge them into a puzzle
  // create a map for to match the full with lines by top and bottom group by the number of tiles
  val dimensionHeightMap = lines.flatMap { listOf(it.top to it, it.bottom to it) }
    .groupBy({ it.first }) { it.second }.toMutableMap()
    .withDefault { emptyList() }

  iteration = 1
  val seen = mutableSetOf<List<List<Long>>>()
  while (iteration != maxCombi) {
    val (result, new) = dimensionHeightMap.flatMap { entry ->
      entry.value.flatMap { image ->
        entry.value.asSequence()
          .filter { it != image }
          .filter { it.idList.intersect(image.idList).isEmpty() }
          .filter { seen.add(image.combination + it.combination) && seen.add(it.combination + image.combination.reversed()) }
          .filter { it.combination.size + image.combination.size <= maxCombi }
          .flatMap { image.matchHeight(it) }
      }
    }.distinctBy { it.combination }.partition { it.combination.size == maxCombi }

    if (result.isNotEmpty()) {
      // results are found. Mutliple actually, but they are mirrors of eachother.
      return result.first()
    }
    new.filter { seen.add(it.combination) }.forEach {
      dimensionHeightMap[it.top] = dimensionHeightMap.getValue(it.top) + it
      dimensionHeightMap[it.bottom] = dimensionHeightMap.getValue(it.bottom) + it
    }
    if (new.isEmpty())
      break
  }
  error("No solution found")
}


const val TILE = '#'

data class Image(
  val idList: List<Long>,
  val data: List<CharSequence>,
  val combination: List<List<Long>>
                ) {


  fun matchWidth(image: Image): List<Image> {
    return cache.computeIfAbsent(this.combination to image.combination) {
      val results = mutableListOf<Image>()
      // Does the piece fit on the left side
      when (leftSide) {
        image.rightSide -> results.add(combine(image, Side.LEFT))
        image.rightSide.reversed() -> results.add(combine(image.flipHorizontal(), Side.LEFT))
        image.leftSide -> results.add(combine(image.flipVertical(), Side.LEFT))
        image.leftSide.reversed() -> results.add(combine(image.flipVertical().flipHorizontal(), Side.LEFT))

      }

      when (rightSide) {
        image.leftSide -> results.add(combine(image, Side.RIGHT))
        image.leftSide.reversed() -> results.add(combine(image.flipHorizontal(), Side.RIGHT))
        image.rightSide -> results.add(combine(image.flipVertical(), Side.RIGHT))
        image.rightSide.reversed() -> results.add(combine(image.flipVertical().flipHorizontal(), Side.RIGHT))

      }

      if (image.idList.size == 1) {
        when (leftSide) {
          image.bottomSide -> results.add(combine(image.rotateLeft().flipHorizontal(), Side.LEFT))
          image.bottomSide.reversed() -> results.add(combine(image.rotateLeft(), Side.LEFT))
          image.topSide -> results.add(combine(image.rotateRight(), Side.LEFT))
          image.topSide.reversed() -> results.add(combine(image.rotateRight().flipHorizontal(), Side.LEFT))
        }
        when (rightSide) {
          image.bottomSide -> results.add(combine(image.rotateRight(), Side.RIGHT))
          image.bottomSide.reversed() -> results.add(combine(image.rotateRight().flipHorizontal(), Side.RIGHT))
          image.topSide -> results.add(combine(image.rotateLeft().flipHorizontal(), Side.RIGHT))
          image.topSide.reversed() -> results.add(combine(image.rotateLeft(), Side.RIGHT))
        }
      }
      results
    }
  }


  fun matchHeight(image: Image): List<Image> {
    return cache.computeIfAbsent(combination to image.combination) {
      val results = mutableListOf<Image>()
      // Does the piece fit on the left side
      when (topSide) {
        image.bottomSide -> results.add(combine(image, Side.TOP))
        image.bottomSide.reversed() -> results.add(combine(image.flipHorizontal(), Side.TOP))
        image.topSide -> results.add(combine(image.flipHorizontal(), Side.TOP))
        image.topSide.reversed() -> results.add(combine(image.flipHorizontal().flipVertical(), Side.TOP))
      }
      when (bottomSide) {
        image.topSide -> results.add(combine(image, Side.BOTTOM))
        image.topSide.reversed() -> results.add(combine(image.flipHorizontal(), Side.BOTTOM))
        image.bottomSide -> results.add(combine(image.flipVertical(), Side.BOTTOM))
        image.bottomSide.reversed() -> results.add(combine(image.flipVertical().flipHorizontal(), Side.BOTTOM))
      }
      results
    }
  }

  fun cornerProduct(): Long {
    return combination.first().first() * combination.first().last() * combination.last().first() * combination.last()
      .last()
  }

  private fun combine(other: Image, side: Side): Image {
    return Image(idList + other.idList,
                 when (side) {
                   Side.TOP -> other.data + data
                   Side.BOTTOM -> data + other.data
                   Side.LEFT -> data.zip(other.data).map { it.second.toString() + it.first }
                   Side.RIGHT -> data.zip(other.data).map { it.first.toString() + it.second }
                 }, when (side) {
                   Side.LEFT -> other.combination.zip(combination).map { (left, right) -> left + right }
                   Side.RIGHT -> combination.zip(other.combination).map { (left, right) -> left + right }
                   Side.TOP -> other.combination + combination
                   Side.BOTTOM -> combination + other.combination
                 }

                )
  }

  fun flipHorizontal(): Image =
    this.copy(data = data.reversed(), combination = combination.reversed())


  fun flipVertical(): Image =
    this.copy(data = data.map { it.reversed() }, combination = combination.map { it.reversed() })

  fun rotateLeft(): Image =
    this.copy(data = data.indices.map { index ->
      data.map { it.dropLast(index).last() }.joinToString("")
    })

  fun rotateRight(): Image =
    this.copy(data = data.indices.map { index ->
      data.reversed().map { it.drop(index).first() }.joinToString("")
    })


  val top: Int by lazy { data.first().count { it == TILE } }
  val bottom: Int by lazy { data.last().count { it == TILE } }
  val left: Int by lazy { data.count { it.first() == TILE } }
  val right: Int by lazy { data.count { it.last() == TILE } }
  val id: String by lazy { combination.joinToString("\n") { it.joinToString("$") } }
  private val leftSide: List<Char> by lazy { data.map { it.first() } }
  private val rightSide: List<Char> by lazy { data.map { it.last() } }
  private val topSide: List<Char> by lazy { data.first().toList() }
  private val bottomSide: List<Char> by lazy { data.last().toList() }

  override fun toString(): String {
    return "Tile ${id}\n${data.joinToString("\n")}\nCombination:\n${combination.joinToString("\n") { it.joinToString(" ") }}}"
  }

}

enum class Side {
  LEFT, RIGHT, TOP, BOTTOM
}

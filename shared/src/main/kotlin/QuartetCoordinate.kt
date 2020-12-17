/**
 * @author verwoerd
 * @since 17-12-20
 */
data class QuartetCoordinate(val x: Int, val y: Int, val z: Int, val w: Int) {
  infix operator fun plus(other: QuartetCoordinate) =
    QuartetCoordinate(x + other.x, y + other.y, z + other.z, w + other.w)
}

fun <V> Map<QuartetCoordinate, V>.range() =
  QuartetCoordinateRange(xRange(), yRange(), zRange(), wRange())

fun <V> Map<QuartetCoordinate, V>.yRange() = keys.yRange()
fun <V> Map<QuartetCoordinate, V>.xRange() = keys.xRange()
fun <V> Map<QuartetCoordinate, V>.zRange() = keys.zRange()
fun <V> Map<QuartetCoordinate, V>.wRange() = keys.wRange()


fun Set<QuartetCoordinate>.range() =
  QuartetCoordinateRange(xRange(), yRange(), zRange(), wRange())

fun Set<QuartetCoordinate>.yRange() = (minByOrNull { it.y }?.y ?: 0)..(maxByOrNull { it.y }?.y ?: 0)
fun Set<QuartetCoordinate>.xRange() = (minByOrNull { it.x }?.x ?: 0)..(maxByOrNull { it.x }?.x ?: 0)
fun Set<QuartetCoordinate>.zRange() = (minByOrNull { it.z }?.z ?: 0)..(maxByOrNull { it.z }?.z ?: 0)
fun Set<QuartetCoordinate>.wRange() = (minByOrNull { it.w }?.w ?: 0)..(maxByOrNull { it.w }?.w ?: 0)

data class QuartetCoordinateRange(
  val xRange: IntRange,
  val yRange: IntRange,
  val zRange: IntRange,
  val wRange: IntRange
                                 ) {
  operator fun contains(coordinate: QuartetCoordinate) =
    coordinate.x in xRange && coordinate.y in yRange && coordinate.z in zRange && coordinate.w in wRange
}

fun adjacentCircularCoordinatesGenerator(origin: QuartetCoordinate) = sequenceOf(-1, 0, 1).flatMap { w ->
  sequenceOf(-1, 0, 1).flatMap { z ->
    sequenceOf(-1, 0, 1).flatMap { y ->
      sequenceOf(-1, 0, 1)
        .filter { x -> x != 0 || y != 0 || z != 0 || w != 0 }
        .map { x -> QuartetCoordinate(origin.x + x, origin.y + y, origin.z + z, origin.w + w) }
    }
  }
}.toList()

// Optimization, this is much faster then recalculating the sequence very call
private val adjacentCircularQuartetList by lazy { adjacentCircularCoordinatesGenerator(QuartetCoordinate(0, 0, 0, 0)) }

fun adjacentCircularCoordinates(origin: QuartetCoordinate) = adjacentCircularQuartetList.asSequence()
  .map { origin + it }

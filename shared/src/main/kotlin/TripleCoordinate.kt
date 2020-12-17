/**
 * @author verwoerd
 * @since 17-12-20
 */
data class TripleCoordinate(val x: Int, val y: Int, val z: Int) : Comparable<TripleCoordinate> {
  override fun compareTo(other: TripleCoordinate): Int = when (val zResult = z.compareTo(other.z)) {
    0 -> when (val yResult = y.compareTo(other.y)) {
      0 -> x.compareTo(other.x)
      else -> yResult
    }
    else -> zResult
  }

}

fun <V> Map<TripleCoordinate, V>.range() =
  TripleCoordinateRange(xRange(), yRange(), zRange())

fun <V> Map<TripleCoordinate, V>.yRange() = keys.yRange()
fun <V> Map<TripleCoordinate, V>.xRange() = keys.xRange()
fun <V> Map<TripleCoordinate, V>.zRange() = keys.zRange()


fun Set<TripleCoordinate>.range() = TripleCoordinateRange(xRange(), yRange(), zRange())
fun Set<TripleCoordinate>.yRange() = minByOrNull { it.y }!!.y..maxByOrNull { it.y }!!.y
fun Set<TripleCoordinate>.xRange() = minByOrNull { it.x }!!.x..maxByOrNull { it.x }!!.x
fun Set<TripleCoordinate>.zRange() = minByOrNull { it.z }!!.z..maxByOrNull { it.z }!!.z


fun adjacentCircularCoordinates(origin: TripleCoordinate) = sequenceOf(
  TripleCoordinate(origin.x, origin.y, origin.z - 1),
  TripleCoordinate(origin.x + 1, origin.y, origin.z - 1),
  TripleCoordinate(origin.x + 1, origin.y + 1, origin.z - 1),
  TripleCoordinate(origin.x + 1, origin.y - 1, origin.z - 1),
  TripleCoordinate(origin.x - 1, origin.y, origin.z - 1),
  TripleCoordinate(origin.x - 1, origin.y - 1, origin.z - 1),
  TripleCoordinate(origin.x - 1, origin.y + 1, origin.z - 1),
  TripleCoordinate(origin.x, origin.y + 1, origin.z - 1),
  TripleCoordinate(origin.x, origin.y - 1, origin.z - 1),
  TripleCoordinate(origin.x + 1, origin.y, origin.z),
  TripleCoordinate(origin.x + 1, origin.y + 1, origin.z),
  TripleCoordinate(origin.x + 1, origin.y - 1, origin.z),
  TripleCoordinate(origin.x - 1, origin.y, origin.z),
  TripleCoordinate(origin.x - 1, origin.y - 1, origin.z),
  TripleCoordinate(origin.x - 1, origin.y + 1, origin.z),
  TripleCoordinate(origin.x, origin.y + 1, origin.z),
  TripleCoordinate(origin.x, origin.y - 1, origin.z),
  TripleCoordinate(origin.x + 1, origin.y, origin.z + 1),
  TripleCoordinate(origin.x + 1, origin.y + 1, origin.z + 1),
  TripleCoordinate(origin.x + 1, origin.y - 1, origin.z + 1),
  TripleCoordinate(origin.x - 1, origin.y, origin.z + 1),
  TripleCoordinate(origin.x - 1, origin.y - 1, origin.z + 1),
  TripleCoordinate(origin.x - 1, origin.y + 1, origin.z + 1),
  TripleCoordinate(origin.x, origin.y + 1, origin.z + 1),
  TripleCoordinate(origin.x, origin.y - 1, origin.z + 1),
  TripleCoordinate(origin.x, origin.y, origin.z + 1)
                                                                      )

data class TripleCoordinateRange(
  val xRange: IntRange,
  val yRange: IntRange,
  val zRange: IntRange
                                ) {
  operator fun contains(coordinate: TripleCoordinate) =
    coordinate.x in xRange && coordinate.y in yRange && coordinate.z in zRange
}

fun IntRange.expand(lower: Int = 1, upper: Int = 1) = (this.first - lower..this.last + upper)

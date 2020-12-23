package day23.part1

import java.io.BufferedReader
import java.util.Deque
import java.util.LinkedList

/**
 * @author verwoerd
 * @since 23/12/2020
 */
fun part1(input: BufferedReader): Any {
  val cups = input.readInput()
  repeat(100) { cups.doMove() }
  return cups.drop(1).joinToString("")
}

fun BufferedReader.readInput() = readLine().asSequence().map { it - '0' }.toCollection(LinkedList())


fun Deque<Int>.doMove(max: Int = 9) {
  val current = removeFirst()
  val pickup1 = removeFirst()
  val pickup2 = removeFirst()
  val pickup3 = removeFirst()

  val destination = findDestination(current, max, pickup1, pickup2, pickup3)
  val rotated = indexOf(destination) + 1
  rotateLeft(rotated)
  offerFirst(pickup3)
  offerFirst(pickup2)
  offerFirst(pickup1)
  rotateRight(rotated)
  offerLast(current)
}

fun findDestination(current: Int, max: Int = 9, pickup1: Int, pickup2: Int, pickup3: Int): Int {
  return when (val next = current - 1) {
    pickup1, pickup2, pickup3 -> findDestination(next, max, pickup1, pickup2, pickup3)
    0 -> findDestination(max + 1, max, pickup1, pickup2, pickup3)
    else -> next
  }
}

fun Deque<Int>.rotateLeft(times: Int) = repeat(times) { offerLast(removeFirst()) }

fun Deque<Int>.rotateRight(times: Int) = repeat(times) { offerFirst(removeLast()) }

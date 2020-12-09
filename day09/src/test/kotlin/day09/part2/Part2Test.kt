package day09.part2

import ExampleTest
import java.io.BufferedReader

class Part2Test : ExampleTest(examples = 1, part = 2, ::wrapper)

fun wrapper(input: BufferedReader) = doPart2(input, 5)

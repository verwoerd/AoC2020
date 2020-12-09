package day09.part1

import ExampleTest
import java.io.BufferedReader

/**
 * @author verwoerd
 * @since 09/12/2020
 */
class Part1Test : ExampleTest(examples = 1, part = 1, ::wrapper)

fun wrapper(input: BufferedReader) = doSolution(input, 5)

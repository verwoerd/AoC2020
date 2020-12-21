package day21.part2

import day21.part1.dangerousIngredients
import day21.part1.readRecipes
import java.io.BufferedReader

/**
 * @author verwoerd
 * @since 21/12/2020
 */
fun part2(input: BufferedReader): Any {
  val recipes = input.readRecipes().toList()
  val unsafeIngredients = recipes.dangerousIngredients()
  return unsafeIngredients.entries.sortedBy { it.key }.joinToString(",") { it.value }
}

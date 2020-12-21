package day21.part1

import day21.part1.Recipe.Companion.parseRecipeFrom
import java.io.BufferedReader

/**
 * @author verwoerd
 * @since 21/12/2020
 */
fun part1(input: BufferedReader): Any {
  val recipes = input.readRecipes().toList()
  val unsafeIngredients = recipes.dangerousIngredients()
  return recipes.sumBy { recipe -> recipe.ingredients.count { it !in unsafeIngredients.values } }
}

val regex = Regex("(?<ingredients>.*) \\(contains (?<allergens>.*)\\)")

fun BufferedReader.readRecipes() = lineSequence().map { parseRecipeFrom(it) }

fun List<Recipe>.dangerousIngredients(): MutableMap<String, String> {
  // create a list that maps allergen to their recipes
  var allergenMap = flatMap { recipe -> recipe.allergens.map { it to recipe } }.groupBy({ it.first }) { it.second }
    .mapValues {
      it.value.fold(it.value.first().ingredients.toSet()) { set, value ->
        set intersect value.ingredients
      }
    }
  val unsafeIngredientsMap = mutableMapOf<String, String>()
  while (allergenMap.keys.isNotEmpty()) {
    allergenMap.filterValues { it.size == 1 }.map { it.key to it.value.first() }.toMap(unsafeIngredientsMap)
    allergenMap = allergenMap.filter { it.key !in unsafeIngredientsMap }
      .mapValues { entry -> entry.value.filter { it !in unsafeIngredientsMap.values }.toSet() }
  }
  return unsafeIngredientsMap
}

data class Recipe(
  val ingredients: List<String>,
  val allergens: List<String>
                 ) {
  companion object {
    fun parseRecipeFrom(line: String): Recipe {
      val (ingredient, allergens) = regex.matchEntire(line)?.destructured ?: error("invalid input")
      return Recipe(ingredient.split(" "), allergens.split(Regex(",? ")))
    }
  }
}

package day21.part1

import day21.part1.Recipe.Companion.parseRecipeFrom
import groupByPair
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

fun List<Recipe>.dangerousIngredients(): MutableMap<String, String> {
  // create a list that maps allergen to their potential sources
  val allergenMap = flatMap { recipe -> recipe.allergens.map { it to recipe } }.groupByPair()
    .mapValues {
      // For each allergen find the intersection of ingredients in common
      it.value.fold(it.value.first().ingredients.toSet()) { set, value ->
        set intersect value.ingredients
      }
    }

  // store the assignments
  val unsafeIngredientsMap = mutableMapOf<String, String>()

  // Unit propagation
  generateSequence(allergenMap) { map ->
    //reduce all clauses
    map.filter { it.key !in unsafeIngredientsMap }
      .mapValues { entry -> entry.value.filter { it !in unsafeIngredientsMap.values }.toSet() }
  }    // find all unit ingredients and store them in the unsafe ingredients map
    .onEach { map -> map.filterValues { it.size == 1 }.map { it.key to it.value.first() }.toMap(unsafeIngredientsMap) }
    // run until there are no more unknowns
    .first { it.isEmpty() }

  return unsafeIngredientsMap
}

val regex = Regex("(?<ingredients>.*) \\(contains (?<allergens>.*)\\)")

fun BufferedReader.readRecipes() = lineSequence().map { parseRecipeFrom(it) }

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

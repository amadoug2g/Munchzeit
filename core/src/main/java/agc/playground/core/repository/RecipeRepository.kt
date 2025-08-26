package agc.playground.core.repository

import agc.playground.core.domain.models.Recipe
import agc.playground.core.domain.models.Tag

/**
 * Created by Amadou on 26/08/2025
 *
 */

interface RecipeRepository {
    fun getAllRecipes(): Result<List<Recipe>>
    fun getRecipe(): Result<Recipe>
    fun searchRecipeByText(text: String): Result<List<Recipe>>
    fun searchRecipeByTag(tags: List<Tag>): Result<List<Recipe>>
}
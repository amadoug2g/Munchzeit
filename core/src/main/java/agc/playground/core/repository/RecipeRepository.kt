package agc.playground.core.repository

import agc.playground.core.domain.models.Recipe
import agc.playground.core.domain.models.Tag

/**
 * Created by Amadou on 26/08/2025
 *
 */

interface RecipeRepository {
    suspend fun getAllRecipes(): Result<List<Recipe>>
    suspend fun getRecipe(recipeId: String): Result<Recipe>
    suspend fun searchRecipesByText(searchQuery: String): Result<List<Recipe>>
    suspend fun searchRecipesByTag(tag: Tag): Result<List<Recipe>>
}
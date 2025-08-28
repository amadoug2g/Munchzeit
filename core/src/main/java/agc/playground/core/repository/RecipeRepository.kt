package agc.playground.core.repository

import agc.playground.core.domain.models.Page
import agc.playground.core.domain.models.Recipe
import agc.playground.core.domain.models.Tag

/**
 * Created by Amadou on 26/08/2025
 *
 */

interface RecipeRepository {
    suspend fun getAllRecipes(limit: Int, skip: Int): Result<Page<Recipe>>
    suspend fun getRecipe(recipeId: String): Result<Recipe>
    suspend fun searchRecipesByText(searchQuery: String, limit: Int, skip: Int): Result<Page<Recipe>>
    suspend fun searchRecipesByTag(tag: Tag): Result<List<Recipe>>
}
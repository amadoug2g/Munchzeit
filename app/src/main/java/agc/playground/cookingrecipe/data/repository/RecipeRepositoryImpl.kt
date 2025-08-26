package agc.playground.cookingrecipe.data.repository

import agc.playground.cookingrecipe.data.api.RecipeApi
import agc.playground.core.domain.models.Recipe
import agc.playground.core.domain.models.Tag
import agc.playground.core.repository.RecipeRepository

/**
 * Created by Amadou on 26/08/2025
 *
 */

class RecipeRepositoryImpl(private val api: RecipeApi): RecipeRepository {
    override suspend fun getAllRecipes(): Result<List<Recipe>> = api.getRecipes()

    override suspend fun getRecipe(recipeId: String): Result<Recipe> {
        return api.getRecipe(recipeId)
    }

    override suspend fun searchRecipesByText(searchQuery: String): Result<List<Recipe>> {
        return api.searchRecipes(searchQuery)
    }

    override suspend fun searchRecipesByTag(tag: Tag): Result<List<Recipe>> {
        return api.searchRecipesByTag(tag)
    }
}
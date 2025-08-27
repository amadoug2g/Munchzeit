package agc.playground.cookingrecipe.data.repository

import agc.playground.cookingrecipe.data.api.RecipeApi
import agc.playground.cookingrecipe.data.mapper.toDomain
import agc.playground.core.domain.models.Recipe
import agc.playground.core.domain.models.Tag
import agc.playground.core.repository.RecipeRepository

/**
 * Created by Amadou on 26/08/2025
 *
 */

class RecipeRepositoryImpl(private val api: RecipeApi) : RecipeRepository {

    override suspend fun getAllRecipes(): Result<List<Recipe>> = runCatching {
        api.getRecipes().recipes.map { it.toDomain() }
    }

    override suspend fun getRecipe(recipeId: String): Result<Recipe> = runCatching {
        api.getRecipe(recipeId.toInt()).toDomain()
    }

    override suspend fun searchRecipesByText(searchQuery: String): Result<List<Recipe>> = runCatching {
        api.searchRecipes(searchQuery).recipes.map { it.toDomain() }
    }

    override suspend fun searchRecipesByTag(tag: Tag): Result<List<Recipe>> = runCatching {
        api.getRecipesByTag(tag.label).recipes.map { it.toDomain() }
    }
}

package agc.playground.cookingrecipe.data.api

import agc.playground.core.domain.models.Recipe
import agc.playground.core.domain.models.Tag
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * Created by Amadou on 26/08/2025
 *
 */

interface RecipeApi {
    @GET("recipes")
    suspend fun getRecipes(): Result<List<Recipe>>

    @GET("recipes/{id}")
    suspend fun getRecipe(@Path("id") recipeId: String): Result<Recipe>

    @GET("recipes/search")
    suspend fun searchRecipes(@Query("q") searchQuery: String): Result<List<Recipe>>

    @GET("recipes/tag/{tag}")
    suspend fun searchRecipesByTag(@Path("tag") tag: Tag): Result<List<Recipe>>
}
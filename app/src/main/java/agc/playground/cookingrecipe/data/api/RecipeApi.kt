package agc.playground.cookingrecipe.data.api

import agc.playground.cookingrecipe.data.models.RecipeDto
import agc.playground.cookingrecipe.data.models.RecipesResponseDto
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * Created by Amadou on 26/08/2025
 *
 */

interface RecipeApi {
    @GET("recipes")
    suspend fun getRecipes(
        @Query("limit") limit: Int,
        @Query("skip") skip: Int
    ): RecipesResponseDto

    @GET("recipes/{id}")
    suspend fun getRecipe(@Path("id") id: Int): RecipeDto

    @GET("recipes/search")
    suspend fun searchRecipes(
        @Query("q") q: String,
        @Query("limit") limit: Int,
        @Query("skip") skip: Int
    ): RecipesResponseDto

    @GET("recipes/tag/{tag}")
    suspend fun getRecipesByTag(@Path("tag") tag: String): RecipesResponseDto
}

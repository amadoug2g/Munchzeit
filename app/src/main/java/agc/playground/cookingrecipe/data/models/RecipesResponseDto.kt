package agc.playground.cookingrecipe.data.models

/**
 * Created by Amadou on 27/08/2025
 *
 */

data class RecipesResponseDto(
    val recipes: List<RecipeDto>,
    val total: Int,
    val skip: Int,
    val limit: Int
)
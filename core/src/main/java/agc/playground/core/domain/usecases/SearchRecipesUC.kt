package agc.playground.core.domain.usecases

import agc.playground.core.repository.RecipeRepository

/**
 * Created by Amadou on 26/08/2025
 *
 */

class SearchRecipesUC (private val repository: RecipeRepository) {
    suspend operator fun invoke(text: String) = repository.searchRecipesByText(text)
}
package agc.playground.core.domain.usecases

import agc.playground.core.repository.RecipeRepository

/**
 * Created by Amadou on 26/08/2025
 *
 */
class SearchRecipeUC (private val repository: RecipeRepository) {
    operator fun invoke(text: String) = repository.searchRecipeByText(text)
}
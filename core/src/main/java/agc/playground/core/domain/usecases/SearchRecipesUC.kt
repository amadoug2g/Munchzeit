package agc.playground.core.domain.usecases

import agc.playground.core.domain.models.Page
import agc.playground.core.domain.models.Recipe
import agc.playground.core.repository.RecipeRepository

/**
 * Created by Amadou on 26/08/2025
 *
 */

class SearchRecipesUC(private val repository: RecipeRepository) {
    suspend operator fun invoke(query: String, limit: Int, skip: Int): Result<Page<Recipe>> =
        repository.searchRecipesByText(query, limit, skip)
}
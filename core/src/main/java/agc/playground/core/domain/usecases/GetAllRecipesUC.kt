package agc.playground.core.domain.usecases

import agc.playground.core.domain.models.Page
import agc.playground.core.domain.models.Recipe
import agc.playground.core.repository.RecipeRepository

/**
 * Created by Amadou on 26/08/2025
 *
 */

class GetAllRecipesUC(private val repository: RecipeRepository) {
    suspend operator fun invoke(limit: Int, skip: Int): Result<Page<Recipe>> =
        repository.getAllRecipes(limit, skip)
}
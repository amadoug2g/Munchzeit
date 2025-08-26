package agc.playground.core.domain.usecases

import agc.playground.core.repository.RecipeRepository

/**
 * Created by Amadou on 26/08/2025
 *
 */

class GetRecipeUC (private val repository: RecipeRepository) {
    operator fun invoke() = repository.getRecipe()
}
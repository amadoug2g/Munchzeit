package agc.playground.core.domain.usecases

import agc.playground.core.domain.models.Tag
import agc.playground.core.repository.RecipeRepository

/**
 * Created by Amadou on 26/08/2025
 *
 */

class GetRecipeByTagUC(private val repository: RecipeRepository) {
    operator fun invoke(tags: List<Tag>) = repository.searchRecipeByTag(tags)
}
package agc.playground.core.domain.usecases

import agc.playground.core.domain.models.Tag
import agc.playground.core.repository.RecipeRepository

/**
 * Created by Amadou on 26/08/2025
 *
 */

class SearchRecipesByTagUC(private val repository: RecipeRepository) {
    suspend operator fun invoke(tag: Tag) = repository.searchRecipesByTag(tag)
}
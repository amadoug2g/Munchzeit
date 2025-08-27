package agc.playground.cookingrecipe.di

import agc.playground.cookingrecipe.data.api.Network
import agc.playground.cookingrecipe.data.repository.RecipeRepositoryImpl
import agc.playground.core.repository.RecipeRepository
import kotlin.getValue

/**
 * Created by Amadou on 27/08/2025
 *
 */

object ServiceLocator {
    private val recipeRepositoryImpl: RecipeRepository by lazy {
        RecipeRepositoryImpl(Network.retrofitService)
    }
    fun recipeRepository(): RecipeRepository = recipeRepositoryImpl
}

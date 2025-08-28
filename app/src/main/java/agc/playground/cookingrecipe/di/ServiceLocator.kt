package agc.playground.cookingrecipe.di

import agc.playground.cookingrecipe.data.api.Network
import agc.playground.cookingrecipe.data.repository.RecipeRepositoryImpl
import agc.playground.cookingrecipe.presentation.recipe.list.RecipeListViewModelFactory
import agc.playground.core.domain.usecases.GetAllRecipesUC
import agc.playground.core.domain.usecases.SearchRecipesByTagUC
import agc.playground.core.domain.usecases.SearchRecipesUC
import agc.playground.core.repository.RecipeRepository
import androidx.lifecycle.ViewModelProvider
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

    fun recipeListVMFactory(): ViewModelProvider.Factory {
        val repo = recipeRepository()
        val getAll = GetAllRecipesUC(repo)
        val search = SearchRecipesUC(repo)
        val searchTags = SearchRecipesByTagUC(repo)
        return RecipeListViewModelFactory(getAll, search, searchTags)
    }
}

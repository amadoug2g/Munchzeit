package agc.playground.cookingrecipe.presentation.recipe.list

/**
 * Created by Amadou on 27/08/2025
 *
 */

import agc.playground.core.domain.usecases.GetAllRecipesUC
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class RecipeListViewModelFactory(
    private val getAllRecipes: GetAllRecipesUC
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return RecipeListViewModel(getAllRecipes) as T
    }
}
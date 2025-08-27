package agc.playground.cookingrecipe.presentation.recipe.list

/**
 * Created by Amadou on 27/08/2025
 *
 */

import agc.playground.core.domain.models.Recipe
import agc.playground.core.domain.usecases.GetAllRecipesUC
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class RecipeListViewModel(
    private val getAllRecipes: GetAllRecipesUC
) : ViewModel() {

    private val _recipes = MutableLiveData<List<Recipe>>(emptyList())
    val recipes: LiveData<List<Recipe>> = _recipes

    fun load() {
        viewModelScope.launch(Dispatchers.IO) {
            val result = getAllRecipes()
            result.onSuccess { _recipes.postValue(it) }
        }
    }
}

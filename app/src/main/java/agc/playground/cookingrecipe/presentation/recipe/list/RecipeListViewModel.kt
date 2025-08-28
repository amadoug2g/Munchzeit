package agc.playground.cookingrecipe.presentation.recipe.list

/**
 * Created by Amadou on 27/08/2025
 *
 */

import agc.playground.core.domain.models.Recipe
import agc.playground.core.domain.models.Tag
import agc.playground.core.domain.usecases.GetAllRecipesUC
import agc.playground.core.domain.usecases.SearchRecipesByTagUC
import agc.playground.core.domain.usecases.SearchRecipesUC
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class RecipeListViewModel(
    private val getAll: GetAllRecipesUC,
    private val searchByText: SearchRecipesUC,
    private val searchByTag: SearchRecipesByTagUC
) : ViewModel() {

    data class UiState(
        val loading: Boolean = false,
        val refreshing: Boolean = false,
        val items: List<Recipe> = emptyList(),
        val emptyMessage: String? = null,
        val query: String = "",
        val activeTag: Tag? = null,
        val skip: Int = 0,
        val limit: Int = 30,
        val total: Int = 0,
        val endReached: Boolean = false
    )

    private val _state = MutableStateFlow(UiState(loading = true))
    val state: StateFlow<UiState> = _state

    init { loadInitial("", null, false) }

    fun onSearchSubmitted(raw: String) {
        val query = raw.trim()
        if (query.length < 2) {
            _state.update { it.copy(emptyMessage = null) }
            return
        }
        loadInitial(query, null, false)
    }

    fun onClear() {
        loadInitial("", _state.value.activeTag, false)
    }

    fun refresh() {
        loadInitial(_state.value.query, _state.value.activeTag, true)
    }

    fun setTag(tag: Tag?) {
        if (_state.value.activeTag == tag) return
        loadInitial("", tag, false)
    }

    fun loadMore() {
        val s = _state.value
        if (s.loading || s.endReached) return
        if (s.activeTag != null) return
        viewModelScope.launch {
            _state.update { it.copy(loading = true) }
            val result = if (s.query.isEmpty())
                getAll(s.limit, s.skip)
            else
                searchByText(s.query, s.limit, s.skip)
            result.onSuccess { page ->
                val newItems = s.items + page.items
                _state.update {
                    it.copy(
                        loading = false,
                        items = newItems,
                        skip = page.skip + page.items.size,
                        total = page.total,
                        endReached = newItems.size >= page.total,
                        emptyMessage = if (newItems.isEmpty()) "No results" else null
                    )
                }
            }.onFailure {
                _state.update { it.copy(loading = false, emptyMessage = "Load failed.") }
            }
        }
    }

    private fun loadInitial(query: String, tag: Tag?, refreshing: Boolean) {
        viewModelScope.launch {
            _state.update {
                it.copy(
                    loading = !refreshing,
                    refreshing = refreshing,
                    query = query,
                    activeTag = tag,
                    skip = 0,
                    endReached = false,
                    emptyMessage = null
                )
            }
            if (tag != null) {
                searchByTag(tag).onSuccess { list ->
                    _state.update {
                        it.copy(
                            loading = false,
                            refreshing = false,
                            items = list,
                            skip = list.size,
                            total = list.size,
                            endReached = true,
                            emptyMessage = if (list.isEmpty()) "No results" else null
                        )
                    }
                }.onFailure {
                    _state.update { it.copy(loading = false, refreshing = false, items = emptyList(), emptyMessage = "Failed to load.") }
                }
            } else {
                val lim = _state.value.limit
                val result = if (query.isEmpty())
                    getAll(lim, 0)
                else
                    searchByText(query, lim, 0)
                result.onSuccess { page ->
                    _state.update {
                        it.copy(
                            loading = false,
                            refreshing = false,
                            items = page.items,
                            skip = page.items.size,
                            total = page.total,
                            endReached = page.items.size >= page.total,
                            emptyMessage = if (page.items.isEmpty()) "No results" else null
                        )
                    }
                }.onFailure {
                    _state.update { it.copy(loading = false, refreshing = false, items = emptyList(), emptyMessage = "Failed to load.") }
                }
            }
        }
    }
}

class RecipeListViewModelFactory(
    private val getAllRecipes: GetAllRecipesUC,
    private val searchRecipes: SearchRecipesUC,
    private val searchByTag: SearchRecipesByTagUC
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return RecipeListViewModel(getAllRecipes, searchRecipes, searchByTag) as T
    }
}

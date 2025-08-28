package agc.playground.cookingrecipe.presentation.recipe.list


import agc.playground.cookingrecipe.testutil.FakeRecipeRepository
import agc.playground.cookingrecipe.testutil.recipe
import agc.playground.core.domain.models.Tag
import agc.playground.core.domain.usecases.GetAllRecipesUC
import agc.playground.core.domain.usecases.SearchRecipesByTagUC
import agc.playground.core.domain.usecases.SearchRecipesUC
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import agc.playground.core.domain.models.Recipe
import kotlinx.coroutines.test.setMain
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

/**
 * Created by Amadou on 28/08/2025
 *
 */

class RecipeListViewModelTest {

    private fun vmWith(vararg items: Recipe): RecipeListViewModel {
        val repo = FakeRecipeRepository(items.toList())
        return RecipeListViewModel(GetAllRecipesUC(repo), SearchRecipesUC(repo), SearchRecipesByTagUC(repo))
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `initial load gets first page`() = runTest {
        Dispatchers.setMain(UnconfinedTestDispatcher(testScheduler))
        try {
            val vm = vmWith(
                recipe(1, "Margherita", listOf(Tag.PIZZA)),
                recipe(2, "Pesto Pasta"),
                recipe(3, "Pepperoni")
            )

            advanceUntilIdle()

            val s = vm.state.value
            assertFalse(s.loading)
            assertEquals(listOf(1,2,3), s.items.map { it.id })
            assertEquals(3, s.total)
            assertEquals(3, s.skip)
            assertTrue(s.endReached)
        } finally {
            Dispatchers.resetMain()
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `setTag loads list and marks endReached`() = runTest {
        Dispatchers.setMain(UnconfinedTestDispatcher(testScheduler))
        try {
            val vm = vmWith(
                recipe(1, "Margherita", listOf(Tag.PIZZA)),
                recipe(2, "Green Salad", listOf(Tag.SALAD))
            )

            vm.setTag(Tag.SALAD)
            advanceUntilIdle()

            val s = vm.state.value
            assertEquals(listOf(2), s.items.map { it.id })
            assertEquals(Tag.SALAD, s.activeTag)
            assertTrue(s.endReached)
            assertEquals(1, s.total)
            assertEquals(1, s.skip)
        } finally {
            Dispatchers.resetMain()
        }
    }
}

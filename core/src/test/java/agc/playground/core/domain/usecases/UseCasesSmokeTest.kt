package agc.playground.core.domain.usecases

import agc.playground.core.domain.models.*
import agc.playground.core.repository.RecipeRepository
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

/**
 * Created by Amadou on 28/08/2025
 *
 */

private class FakeRecipeRepository(
    private val data: List<Recipe>
) : RecipeRepository {
    override suspend fun getAllRecipes(limit: Int, skip: Int) =
        Result.success(
            Page(
                items = data.drop(skip).take(limit),
                total = data.size,
                skip = skip,
                limit = limit
            )
        )

    override suspend fun getRecipe(recipeId: String) =
        data.firstOrNull { it.id.toString() == recipeId }
            ?.let { Result.success(it) }
            ?: Result.failure(NoSuchElementException(recipeId))

    override suspend fun searchRecipesByText(searchQuery: String, limit: Int, skip: Int): Result<Page<Recipe>> {
        val filtered = data.filter { it.name.contains(searchQuery, ignoreCase = true) }
        return Result.success(
            Page(
                items = filtered.drop(skip).take(limit),
                total = filtered.size,
                skip = skip,
                limit = limit
            )
        )
    }

    override suspend fun searchRecipesByTag(tag: Tag) =
        Result.success(data.filter { tag in it.tags })
}

private fun recipe(
    id: Int,
    name: String,
    tags: List<Tag> = emptyList()
) = Recipe(
    id = id,
    name = name,
    ingredients = emptyList(),
    instructions = emptyList(),
    prepTimeMinutes = 0,
    cookTimeMinutes = 0,
    difficulty = "Easy",
    tags = tags,
    image = "",
    rating = 0.0
)

class UseCasesSmokeTest {

    @Test
    fun getRecipe_returns_item_by_id() = runTest {
        val repo = FakeRecipeRepository(listOf(recipe(42, "Margherita", listOf(Tag.PIZZA))))
        val uc = GetRecipeUC(repo)

        val result = uc("42")

        assertTrue(result.isSuccess)
        assertEquals(42, result.getOrThrow().id)
    }

    @Test
    fun searchText_returns_paged_filtered_items() = runTest {
        val repo = FakeRecipeRepository(
            listOf(
                recipe(1, "Margherita"),
                recipe(2, "Pesto Pasta"),
                recipe(3, "Pepperoni")
            )
        )
        val uc = SearchRecipesUC(repo)

        val page = uc("pe", limit = 2, skip = 0).getOrThrow()

        assertEquals(listOf(2, 3), page.items.map { it.id })
        assertEquals(2, page.limit)
        assertEquals(2, page.total)
    }
}
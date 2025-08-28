package agc.playground.cookingrecipe.testutil

import agc.playground.core.domain.models.Page
import agc.playground.core.domain.models.Recipe
import agc.playground.core.domain.models.Tag
import agc.playground.core.repository.RecipeRepository

/**
 * Created by Amadou on 28/08/2025
 *
 */

class FakeRecipeRepository(
    private val data: List<Recipe>
) : RecipeRepository {
    override suspend fun getAllRecipes(limit: Int, skip: Int): Result<Page<Recipe>> =
        Result.success(Page(data.drop(skip).take(limit), data.size, skip, limit))

    override suspend fun getRecipe(recipeId: String): Result<Recipe> =
        data.firstOrNull { it.id.toString() == recipeId }?.let { Result.success(it) }
            ?: Result.failure(NoSuchElementException(recipeId))

    override suspend fun searchRecipesByText(
        searchQuery: String,
        limit: Int,
        skip: Int
    ): Result<Page<Recipe>> {
        val filtered = data.filter { it.name.contains(searchQuery, ignoreCase = true) }
        return Result.success(Page(filtered.drop(skip).take(limit), filtered.size, skip, limit))
    }

    override suspend fun searchRecipesByTag(tag: Tag): Result<List<Recipe>> =
        Result.success(data.filter { tag in it.tags })
}

fun recipe(
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
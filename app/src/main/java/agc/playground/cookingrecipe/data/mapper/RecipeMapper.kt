package agc.playground.cookingrecipe.data.mapper

import agc.playground.cookingrecipe.data.models.RecipeDto
import agc.playground.cookingrecipe.data.models.RecipeUiModel
import agc.playground.cookingrecipe.data.models.RecipesResponseDto
import agc.playground.core.domain.models.Page
import agc.playground.core.domain.models.Recipe
import agc.playground.core.domain.models.Tag

/**
 * Created by Amadou on 26/08/2025
 *
 */

fun RecipeDto.toDomain(): Recipe = Recipe(
    id = this.id,
    name = this.name,
    ingredients = this.ingredients,
    instructions = this.instructions,
    prepTimeMinutes = this.prepTimeMinutes,
    cookTimeMinutes = this.cookTimeMinutes,
    difficulty = this.difficulty,
    tags = this.tags.mapNotNull(Tag::fromLabel),
    image = this.image,
    rating = this.rating
)

fun RecipesResponseDto.toDomainPage(): Page<Recipe> =
    Page(
        items = recipes.map { it.toDomain() },
        total = total,
        skip = skip,
        limit = limit
    )

fun Recipe.toUiModel() = RecipeUiModel(
    id, name, image, rating, difficulty,
    ingredients, instructions, prepTimeMinutes, cookTimeMinutes
)

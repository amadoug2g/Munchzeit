package agc.playground.core.domain.models

/**
 * Created by Amadou on 26/08/2025
 *
 */

data class Recipe(
    val id: Int,
    val name: String,
    val ingredients: List<String>,
    val instructions: List<String>,
    val prepTimeMinutes: Int,
    val cookTimeMinutes: Int,
    val difficulty: String,
    val tags: List<Tag>,
    val image: String,
    val rating: Double,
)

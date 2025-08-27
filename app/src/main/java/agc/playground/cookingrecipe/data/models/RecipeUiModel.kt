package agc.playground.cookingrecipe.data.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

/**
 * Created by Amadou on 27/08/2025
 *
 */

@Parcelize
data class RecipeUiModel(
    val id: Int,
    val name: String,
    val image: String,
    val rating: Double,
    val difficulty: String,
    val ingredients: List<String>,
    val instructions: List<String>,
    val prepTimeMinutes: Int,
    val cookTimeMinutes: Int
) : Parcelable
package agc.playground.cookingrecipe.presentation.common

/**
 * Created by Amadou on 27/08/2025
 *
 */

import android.widget.TextView
import androidx.databinding.BindingAdapter
import agc.playground.core.domain.models.Tag

@BindingAdapter("tagsText")
fun TextView.setTagsText(tags: List<Tag>?) {
    text = tags?.joinToString(" • ") { it.label }.orEmpty()
}

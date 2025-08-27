package agc.playground.cookingrecipe.presentation.common

/**
 * Created by Amadou on 27/08/2025
 *
 */

import android.widget.TextView
import androidx.databinding.BindingAdapter
import agc.playground.core.domain.models.Tag
import android.widget.ImageView
import coil3.load
import coil3.request.crossfade
import coil3.request.transformations
import coil3.transform.RoundedCornersTransformation

@BindingAdapter("tagsText")
fun TextView.setTagsText(tags: List<Tag>?) {
    text = tags?.joinToString(" • ") { it.label }.orEmpty()
}

@BindingAdapter("imageUrl")
fun ImageView.bindImageUrl(url: String?) {
    if (url.isNullOrBlank()) {
        setImageDrawable(null)
        return
    }
    load(url) {
        crossfade(true)
        transformations(
            RoundedCornersTransformation(8f)
        )
    }
}

@BindingAdapter("linesFromList")
fun TextView.setLinesFromList(items: List<String>?) {
    text = items?.joinToString("\n") { "• $it" }.orEmpty()
}

@BindingAdapter("numberedLinesFromList")
fun TextView.setNumberedLinesFromList(items: List<String>?) {
    text = items?.joinToString("\n") { "• $it" }.orEmpty()
}
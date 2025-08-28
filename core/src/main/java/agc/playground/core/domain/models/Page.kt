package agc.playground.core.domain.models

/**
 * Created by Amadou on 28/08/2025
 *
 */

data class Page<T>(
    val items: List<T>,
    val total: Int,
    val skip: Int,
    val limit: Int
)

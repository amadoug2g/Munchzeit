package agc.playground.core.domain.models

import org.junit.jupiter.api.Assertions.*
import kotlin.test.Test

/**
 * Created by Amadou on 28/08/2025
 *
 */

class PageTest {
    @Test
    fun holds_items_and_paging_meta() {
        val items = listOf(1, 2, 3)
        val page = Page(items = items, total = 10, skip = 3, limit = 3)

        assertEquals(items, page.items)
        assertEquals(10, page.total)
        assertEquals(3, page.skip)
        assertEquals(3, page.limit)
    }
}
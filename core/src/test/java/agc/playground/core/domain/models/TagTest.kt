package agc.playground.core.domain.models

import org.junit.jupiter.api.Assertions.*

/**
 * Created by Amadou on 28/08/2025
 *
 */

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNull


class TagTest {

    @Test
    fun fromLabel_returnsCorrectEnum_whenLabelMatches() {
        val tag = Tag.fromLabel("Pizza")
        assertEquals(Tag.PIZZA, tag)
    }

    @Test
    fun fromLabel_ignoresCaseSensitivity() {
        val tag = Tag.fromLabel("pIzZa")
        assertEquals(Tag.PIZZA, tag)
    }

    @Test
    fun fromLabel_worksWithLabelsContainingSpaces() {
        val tag = Tag.fromLabel("Main course")
        assertEquals(Tag.MAIN_COURSE, tag)
    }

    @Test
    fun fromLabel_returnsNull_forUnknownLabel() {
        val tag = Tag.fromLabel("Nonexistent")
        assertNull(tag)
    }
}

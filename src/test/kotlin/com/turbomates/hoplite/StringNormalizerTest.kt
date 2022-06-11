package com.turbomates.hoplite

import kotlin.test.assertEquals
import org.junit.jupiter.api.Test

class StringNormalizerTest {
    @Test
    fun `test normalizer underscore`() {
        assertEquals("123.123.123", "123_123_123".normalize(true, false))
    }

    @Test
    fun `test normalizer as is`() {
        assertEquals("123_123_123", "123_123_123".normalize(false, false))
    }

    @Test
    fun `test normalizer allow uppercase`() {
        assertEquals("ourUppercaseName", "OUR_UPPERCASE_NAME".normalize(false, true))
    }

    @Test
    fun `test normalizer allow uppercase dot `() {
        assertEquals("our.uppercase.name", "OUR.UPPERCASE.NAME".normalize(false, true))
    }

    @Test
    fun `test normalizer allow uppercase an underscore`() {
        assertEquals("our.uppercase.name", "OUR_UPPERCASE_NAME".normalize(true, true))
    }
}

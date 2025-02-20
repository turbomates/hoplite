package com.turbomates.hoplite

import com.sksamuel.hoplite.ConfigException
import com.sksamuel.hoplite.ConfigLoaderBuilder
import com.sksamuel.hoplite.addResourceSource
import org.junit.jupiter.api.assertThrows
import kotlin.test.Test
import kotlin.test.assertEquals

class PrefixRemovalPreprocessorTest {
    @Test
    fun `test prefix removal`() {
        val config = ConfigLoaderBuilder.default()
            .addResourceSource("/test.properties")
            .addResourceSource("/prefix_test.properties")
            .addPreprocessor(PrefixRemovalPreprocessor("prefix"))
            .build()
            .loadConfigOrThrow<MyConfig>()
        assertEquals(2, config.test.count)
    }

    @Test
    fun `test prefix removal exception`() {
        assertThrows<ConfigException> {
            ConfigLoaderBuilder.default()
                .addResourceSource("/test.properties")
                .build()
                .loadConfigOrThrow<MyConfig>()
        }
    }

    data class MyConfig(val test: Test) {
        data class Test(val count: Int, val matrix: Int)
    }
}

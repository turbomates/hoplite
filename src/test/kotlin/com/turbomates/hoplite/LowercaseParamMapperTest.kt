package com.turbomates.hoplite

import com.sksamuel.hoplite.ConfigException
import com.sksamuel.hoplite.ConfigLoaderBuilder
import com.sksamuel.hoplite.addResourceSource
import org.junit.jupiter.api.assertThrows
import kotlin.test.Test
import kotlin.test.assertEquals

class LowercaseParamMapperTest {
    @Test
    fun `test lowercase param mapper`() {
        val config = ConfigLoaderBuilder.default()
            .addResourceSource("/test.properties")
            .addParameterMapper(LowercaseParamMapper)
            .build()
            .loadConfigOrThrow<MyConfig>()
        assertEquals(2, config.hopliteCount)
    }

    @Test
    fun `test lowercase param mapper exception`() {
        assertThrows<ConfigException> {
            ConfigLoaderBuilder.default()
                .addResourceSource("/test.properties")
                .build()
                .loadConfigOrThrow<MyConfig>()
        }
    }

    data class MyConfig(val hopliteCount: Int)
}

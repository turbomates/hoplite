package com.turbomates.hoplite

import com.sksamuel.hoplite.ConfigLoaderBuilder
import com.sksamuel.hoplite.addResourceSource
import kotlin.test.Test
import kotlin.test.assertEquals

class LowercaseParamMapperTest {
    @Test
    fun `test lowercase param mapper`() {
        val config = ConfigLoaderBuilder.default()
            .addResourceSource("/test.properties")
            .build()
            .loadConfigOrThrow<MyConfig>()
        assertEquals(2, config.hopliteCount)
    }

    data class MyConfig(val hopliteCount: Int)
}

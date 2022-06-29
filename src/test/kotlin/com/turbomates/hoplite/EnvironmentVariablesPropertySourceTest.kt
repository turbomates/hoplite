package com.turbomates.hoplite

import com.sksamuel.hoplite.ConfigLoaderBuilder
import com.sksamuel.hoplite.ConfigResult
import com.sksamuel.hoplite.Node
import com.sksamuel.hoplite.PropertySource
import com.sksamuel.hoplite.PropertySourceContext
import com.sksamuel.hoplite.addResourceSource
import com.sksamuel.hoplite.fp.valid
import com.sksamuel.hoplite.parsers.toNode
import java.util.Properties
import kotlin.test.Test
import kotlin.test.assertEquals

class EnvironmentVariablesPropertySourceTest {

    @Test
    fun `env values with underscore`() {
        val config = ConfigLoaderBuilder.default()
            .addPreprocessor(PrefixRemovalPreprocessor("PREFIX"))
            .addPropertySource(EnvironmentVariablesPropertySource(true, false) { mapOf("PREFIX_COUNT" to "2").mapKeys { it.key.lowercase() } })
            .build()
            .loadConfigOrThrow<MyConfig>()
        assertEquals(2, config.count)
    }

    data class MyConfig(val count: Int)
}

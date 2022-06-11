package com.turbomates.hoplite.vault

import com.sksamuel.hoplite.ConfigLoaderBuilder
import kotlin.test.assertEquals
import org.junit.jupiter.api.Test
import org.mockito.kotlin.any
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock

class VaultPreprocessorTest {
    @Test
    fun `load from vault`() {
        val api = mock<VaultAPI> {
            onBlocking {
                read(any(), any())
            } doReturn (mapOf("count" to "2"))
        }
        val vault = ConfigLoaderBuilder.default()
            .addPropertySource(VaultPropertySource(useUnderscoresAsSeparator = true, allowUppercaseNames = true, api, "test", "test"))
            .build()
            .loadConfigOrThrow<MyConfig>()
        assertEquals(2, vault.count)
    }

    data class MyConfig(val count: Int)
}

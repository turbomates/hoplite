package com.turbomates.hoplite.aws

import com.sksamuel.hoplite.ConfigLoaderBuilder
import org.junit.jupiter.api.Test
import org.mockito.kotlin.any
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock
import kotlin.test.assertEquals

class AWSSecretManagerPropertySourceTest {
    @Test
    fun `load from aws`() {
        val api = mock<SecretManagerAPI> {
            onBlocking {
                secret(any())
            } doReturn mapOf("count" to "2")
        }
        val vault = ConfigLoaderBuilder.default()
            .addPropertySource(
                AWSSecretManagerPropertySource(
                    useUnderscoresAsSeparator = true,
                    allowUppercaseNames = true,
                    api = api,
                    secretName = "test"
                )
            )
            .build()
            .loadConfigOrThrow<MyConfig>()
        assertEquals(2, vault.count)
    }

    data class MyConfig(val count: Int)
}

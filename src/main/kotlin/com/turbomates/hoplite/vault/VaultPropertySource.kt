package com.turbomates.hoplite.vault

import com.sksamuel.hoplite.ConfigResult
import com.sksamuel.hoplite.Node
import com.sksamuel.hoplite.PropertySource
import com.sksamuel.hoplite.PropertySourceContext
import com.sksamuel.hoplite.fp.valid
import com.sksamuel.hoplite.parsers.toNode
import com.turbomates.hoplite.normalize
import kotlinx.coroutines.runBlocking

class VaultPropertySource(
    private val useUnderscoresAsSeparator: Boolean,
    private val allowUppercaseNames: Boolean,
    private val api: VaultAPI,
    private val namespace: String,
    private val key: String,
) : PropertySource {
    override fun node(context: PropertySourceContext): ConfigResult<Node> {
        return runBlocking {
            api.read(namespace, key).mapKeys {
                it.key.normalize(useUnderscoresAsSeparator, allowUppercaseNames)
            }.toNode("vault").valid()
        }
    }

    override fun source(): String = "Vault"
}

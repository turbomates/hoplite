package com.turbomates.hoplite.aws

import com.sksamuel.hoplite.ConfigResult
import com.sksamuel.hoplite.Node
import com.sksamuel.hoplite.PropertySource
import com.sksamuel.hoplite.PropertySourceContext
import com.sksamuel.hoplite.fp.valid
import com.sksamuel.hoplite.parsers.toNode
import com.turbomates.hoplite.normalize
import kotlinx.coroutines.runBlocking

class AWSSecretManagerPropertySource(
    private val useUnderscoresAsSeparator: Boolean,
    private val allowUppercaseNames: Boolean,
    private val api: SecretManagerAPI,
    private val secretName: String
) : PropertySource {
    override fun node(context: PropertySourceContext): ConfigResult<Node> = runBlocking {
        api.secret(secretName).mapKeys { secret ->
            secret.key.normalize(useUnderscoresAsSeparator, allowUppercaseNames)
        }.toNode("amazon").valid()
    }

    override fun source(): String = "Amazon Secrets Manager"
}


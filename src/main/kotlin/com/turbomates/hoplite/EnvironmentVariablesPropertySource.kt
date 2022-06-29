package com.turbomates.hoplite

import com.sksamuel.hoplite.ConfigResult
import com.sksamuel.hoplite.Node
import com.sksamuel.hoplite.PropertySource
import com.sksamuel.hoplite.PropertySourceContext
import com.sksamuel.hoplite.fp.valid
import com.sksamuel.hoplite.parsers.toNode
import java.util.Properties

class EnvironmentVariablesPropertySource(
    private val useUnderscoresAsSeparator: Boolean,
    private val allowUppercaseNames: Boolean,
    private val environmentVariableMap: () -> Map<String, String> = { System.getenv() },
) : PropertySource {

    override fun source(): String = "Env Var"
    override fun node(context: PropertySourceContext): ConfigResult<Node> {
        val props = Properties()
        environmentVariableMap().forEach {
            val key = it.key.normalize(useUnderscoresAsSeparator, allowUppercaseNames)
            props[key] = it.value
        }
        return props.toNode("env").valid()
    }
}

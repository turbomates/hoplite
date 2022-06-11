package com.turbomates.hoplite.vault

import com.sksamuel.hoplite.ConfigResult
import com.sksamuel.hoplite.Node
import com.sksamuel.hoplite.PrimitiveNode
import com.sksamuel.hoplite.StringNode
import com.sksamuel.hoplite.fp.valid
import com.sksamuel.hoplite.parsers.toNode
import com.sksamuel.hoplite.preprocessor.TraversingPrimitivePreprocessor
import kotlinx.coroutines.runBlocking

class VaultPreprocessor(private val vaultAPI: VaultAPI) : TraversingPrimitivePreprocessor() {
    // example jdbc.database=vault:gambling/database
    private val regex = "vault:(.*?)/(.*?)".toRegex()

    override fun handle(node: PrimitiveNode): ConfigResult<Node> =
        when (node) {
            is StringNode ->
                when (val match = regex.matchEntire(node.value)) {
                    null -> node.valid()
                    else -> {
                        val data = runBlocking {
                            vaultAPI.read(
                                match.groups[1]!!.value,
                                match.groups[2]!!.value
                            )
                        }

                        if (data.isEmpty()) {
                            node.valid()
                        } else {
                            process(data.toNode("vault"))
                        }
                    }
                }
            else -> {
                node.valid()
            }
        }
}

package com.turbomates.hoplite

import com.sksamuel.hoplite.*
import com.sksamuel.hoplite.fp.valid
import com.sksamuel.hoplite.preprocessor.Preprocessor

class PrefixRemovalPreprocessor(private val prefix: String) : Preprocessor {

    override fun process(node: Node, context: DecoderContext): ConfigResult<Node> {
        val newNode = when (node) {
            is MapNode -> {
                val prefixNode = node.map[prefix.lowercase()] ?: node.map[prefix.uppercase()]
                val nodeMap = if (prefixNode is MapNode) node.map + prefixNode.map else node.map
                MapNode(
                    nodeMap.map { (k, v) -> k to process(v, context).getUnsafe() }.toMap(),
                    node.pos,
                    node.path,
                    node.value
                )
            }

            is ArrayNode -> ArrayNode(node.elements.map { process(it, context).getUnsafe() }, node.pos, node.path)
            is PrimitiveNode -> node
            else -> node
        }

        return newNode.valid()
    }
}

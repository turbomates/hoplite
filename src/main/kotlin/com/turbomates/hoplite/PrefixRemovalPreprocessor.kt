package com.turbomates.hoplite

import com.sksamuel.hoplite.ArrayNode
import com.sksamuel.hoplite.ConfigResult
import com.sksamuel.hoplite.MapNode
import com.sksamuel.hoplite.Node
import com.sksamuel.hoplite.PrimitiveNode
import com.sksamuel.hoplite.fp.valid
import com.sksamuel.hoplite.preprocessor.Preprocessor
import com.sksamuel.hoplite.preprocessor.TraversingPrimitivePreprocessor

class PrefixRemovalPreprocessor(private val prefix: String) : Preprocessor {
    override fun process(node: Node): ConfigResult<Node> {
        val newNode = when (node) {
            is MapNode -> {
                val nodeMap = when (val prefixNode = node.map[prefix]) {
                    is MapNode -> node.map + prefixNode.map
                    else -> node.map
                }

                MapNode(nodeMap.map { (k, v) -> k to process(v).getUnsafe() }.toMap(), node.pos, node.path, node.value)
            }

            is ArrayNode -> ArrayNode(node.elements.map { process(it).getUnsafe() }, node.pos, node.path)
            is PrimitiveNode -> node
            else -> node
        }

        return newNode.valid()
    }
}

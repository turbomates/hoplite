package com.turbomates.hoplite

import com.sksamuel.hoplite.ParameterMapper
import kotlin.reflect.KParameter

object LowercaseParamMapper : ParameterMapper {
    override fun map(param: KParameter): Set<String> = param.name?.run { setOf(lowercase()) } ?: setOf("<anon>")
}

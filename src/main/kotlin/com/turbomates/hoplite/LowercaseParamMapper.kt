package com.turbomates.hoplite

import com.sksamuel.hoplite.ParameterMapper
import kotlin.reflect.KClass
import kotlin.reflect.KFunction
import kotlin.reflect.KParameter

object LowercaseParamMapper : ParameterMapper {
    override fun map(param: KParameter, constructor: KFunction<Any>, kclass: KClass<*>): Set<String> {
        return param.name?.run { setOf(lowercase()) } ?: setOf("<anon>")
    }
}

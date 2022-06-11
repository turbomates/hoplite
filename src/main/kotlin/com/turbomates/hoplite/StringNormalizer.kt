package com.turbomates.hoplite

internal fun String.normalize(useUnderscoresAsSeparator: Boolean, allowUppercaseNames: Boolean): String {
    return this.let { key -> if (useUnderscoresAsSeparator) key.replace("_", ".") else key }
        .let { key ->
            if (allowUppercaseNames && Character.isUpperCase(key.codePointAt(0))) {
                key.split(".").joinToString(separator = ".") { value ->
                    value.fold("") { acc, char ->
                        when {
                            acc.isEmpty() -> acc + char.lowercaseChar()
                            acc.last() == '_' -> acc.dropLast(1) + char.uppercaseChar()
                            else -> acc + char.lowercaseChar()
                        }
                    }
                }
            } else {
                key
            }
        }
}

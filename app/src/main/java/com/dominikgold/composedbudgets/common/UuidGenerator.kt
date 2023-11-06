package com.dominikgold.composedbudgets.common

import java.util.UUID

interface UuidGenerator {

    fun generate(): String
}

class DefaultUuidGenerator : UuidGenerator {

    override fun generate(): String {
        return UUID.randomUUID().toString()
    }
}

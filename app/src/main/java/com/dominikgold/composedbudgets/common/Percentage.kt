package com.dominikgold.composedbudgets.common

import kotlin.math.roundToInt

@JvmInline
value class Percentage(val value: Float) {

    init {
        require(value in 0f..1f)
    }

    val intValue get() = (value * 100).roundToInt()
}

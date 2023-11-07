package com.dominikgold.composedbudgets.common

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlin.math.roundToInt

@Parcelize
@JvmInline
value class Percentage(val value: Float) : Parcelable {

    init {
        require(value in 0f..1f)
    }

    val intValue get() = (value * 100).roundToInt()
}

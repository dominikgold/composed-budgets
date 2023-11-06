package com.dominikgold.composedbudgets.common

fun String.parseUserInputToDouble(): Double? = if (this.endsWith(",") || this.endsWith(".")) {
    (this + "0").toDoubleOrNull()
} else {
    this.toDoubleOrNull()
}

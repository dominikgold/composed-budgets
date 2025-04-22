package com.dominikgold.composedbudgets.utils

import timber.log.Timber

actual object Logger {

    actual fun log(message: String) {
        Timber.d(message)
    }

    fun init() {
        Timber.plant(Timber.DebugTree())
    }
}

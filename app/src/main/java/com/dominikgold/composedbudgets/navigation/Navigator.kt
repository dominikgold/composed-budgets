package com.dominikgold.composedbudgets.navigation

import androidx.compose.runtime.staticCompositionLocalOf

interface Navigator {

    fun navigateTo(destination: Destination)

    fun goBack()
}

val LocalNavigator = staticCompositionLocalOf<Navigator> { error("Navigator local not initialized") }

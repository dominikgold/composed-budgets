package com.dominikgold.composedbudgets.navigation

import com.dominikgold.composedbudgets.utils.coroutines.UiEventsFlow

interface Navigator {

    val navigationEvents: UiEventsFlow<Destination>

    val goBackEvents: UiEventsFlow<Unit>

    fun navigateTo(destination: Destination)

    fun goBack()
}

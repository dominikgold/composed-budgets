package com.dominikgold.composedbudgets.navigation

import com.dominikgold.composedbudgets.utils.coroutines.UiEventsFlow

class AppNavigator : Navigator {

    override val navigationEvents = UiEventsFlow<Destination>()
    override val goBackEvents = UiEventsFlow<Unit>()

    override fun navigateTo(destination: Destination) {
        navigationEvents.tryEmit(destination)
    }

    override fun goBack() {
        goBackEvents.tryEmit(Unit)
    }
}

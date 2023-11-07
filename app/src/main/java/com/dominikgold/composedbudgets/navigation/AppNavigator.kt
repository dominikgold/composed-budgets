package com.dominikgold.composedbudgets.navigation

import com.dominikgold.composedbudgets.common.throttleFirst
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow

const val NAVIGATION_DEBOUNCE_MILLIS = 300L

class AppNavigator : Navigator, NavigationEvents {

    private val navEventsChannel = Channel<Destination>(Channel.BUFFERED)
    override val navEvents = navEventsChannel.receiveAsFlow()
        .throttleFirst(NAVIGATION_DEBOUNCE_MILLIS)

    private val goBackEventsChannel = Channel<Unit>(Channel.BUFFERED)
    override val goBackEvents = goBackEventsChannel.receiveAsFlow()

    override fun navigateTo(destination: Destination) {
        navEventsChannel.trySend(destination)
    }

    override fun goBack() {
        goBackEventsChannel.trySend(Unit)
    }
}

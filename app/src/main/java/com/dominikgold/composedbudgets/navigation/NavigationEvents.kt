package com.dominikgold.composedbudgets.navigation

import kotlinx.coroutines.flow.Flow

interface NavigationEvents {

    val goBackEvents: Flow<Unit>

    val navEvents: Flow<Destination>
}

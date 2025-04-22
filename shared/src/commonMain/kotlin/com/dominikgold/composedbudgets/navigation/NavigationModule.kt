package com.dominikgold.composedbudgets.navigation

import org.koin.dsl.bind
import org.koin.dsl.module

val navigationModule = module {
    single { AppNavigator() } bind Navigator::class
}

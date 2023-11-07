package com.dominikgold.composedbudgets

import com.dominikgold.composedbudgets.common.DateTimeProvider
import com.dominikgold.composedbudgets.common.DefaultDateTimeProvider
import com.dominikgold.composedbudgets.common.DefaultUuidGenerator
import com.dominikgold.composedbudgets.common.UuidGenerator
import com.dominikgold.composedbudgets.database.databaseModule
import com.dominikgold.composedbudgets.features.budgets.di.budgetsModule
import com.dominikgold.composedbudgets.features.expenses.di.expensesModule
import com.dominikgold.composedbudgets.features.overview.di.budgetsOverviewModule
import com.dominikgold.composedbudgets.navigation.AppNavigator
import com.dominikgold.composedbudgets.navigation.NavigationEvents
import com.dominikgold.composedbudgets.navigation.Navigator
import org.koin.dsl.bind
import org.koin.dsl.binds
import org.koin.dsl.module

private val navigationModule = module {
    single { AppNavigator() } binds arrayOf(Navigator::class, NavigationEvents::class)
}

private val utilsModule = module {
    factory { DefaultDateTimeProvider() } bind DateTimeProvider::class
    factory { DefaultUuidGenerator() } bind UuidGenerator::class
}

val appModule = module {
    includes(utilsModule, navigationModule, databaseModule, budgetsOverviewModule, budgetsModule, expensesModule)
}

package com.dominikgold.composedbudgets.android.di

import com.dominikgold.composedbudgets.android.features.expenses.di.androidExpensesModule
import com.dominikgold.composedbudgets.android.features.home.di.androidHomeModule
import com.dominikgold.composedbudgets.database.di.androidDatabaseModule
import com.dominikgold.composedbudgets.navigation.navigationModule
import com.dominikgold.composedbudgets.utils.PlatformContext
import com.dominikgold.composedbudgets.utils.di.utilsModule
import org.koin.dsl.module

val appModule = module {
    single { PlatformContext(get()) }
    includes(
        navigationModule,
        utilsModule,
        androidDatabaseModule,
        androidHomeModule,
        androidExpensesModule,
    )
}

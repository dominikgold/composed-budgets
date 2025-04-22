package com.dominikgold.composedbudgets.database.di

import com.dominikgold.composedbudgets.database.DatabaseDriverFactory
import org.koin.dsl.module

val androidDatabaseModule = module {
    single { DatabaseDriverFactory(get()) }
    includes(databaseModule)
}

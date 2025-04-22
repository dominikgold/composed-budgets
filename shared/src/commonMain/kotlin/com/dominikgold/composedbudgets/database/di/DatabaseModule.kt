package com.dominikgold.composedbudgets.database.di

import com.dominikgold.composedbudgets.database.AppDatabase
import org.koin.dsl.module

val databaseModule = module {
    single { AppDatabase(get()) }
}

package com.dominikgold.composedbudgets.database

class AppDatabase(databaseDriverFactory: DatabaseDriverFactory) {

    val instance = ComposedBudgetsDatabase(databaseDriverFactory.createDriver())
}

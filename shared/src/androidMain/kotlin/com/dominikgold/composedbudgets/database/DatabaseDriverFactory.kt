package com.dominikgold.composedbudgets.database

import android.content.Context
import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.android.AndroidSqliteDriver

actual class DatabaseDriverFactory(private val appContext: Context) {

    actual fun createDriver(): SqlDriver {
        return AndroidSqliteDriver(ComposedBudgetsDatabase.Schema, appContext, "composedbudgets.db")
    }
}

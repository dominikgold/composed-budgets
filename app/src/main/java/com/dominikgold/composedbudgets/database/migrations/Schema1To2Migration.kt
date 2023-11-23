package com.dominikgold.composedbudgets.database.migrations

import androidx.room.DeleteColumn
import androidx.room.migration.AutoMigrationSpec

@DeleteColumn(tableName = "budgets", columnName = "excessCarryOver")
@DeleteColumn(tableName = "budgets", columnName = "overdraftCarryOver")
class Schema1To2Migration : AutoMigrationSpec

package com.dominikgold.composedbudgets.database

import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.RoomDatabase
import com.dominikgold.composedbudgets.database.budgets.BudgetsDao
import com.dominikgold.composedbudgets.database.budgets.PersistedBudget
import com.dominikgold.composedbudgets.database.expenses.ExpensesDao
import com.dominikgold.composedbudgets.database.expenses.PersistedExpense
import com.dominikgold.composedbudgets.database.migrations.Schema1To2Migration

@Database(
    entities = [PersistedBudget::class, PersistedExpense::class],
    version = 2,
    autoMigrations = [
        AutoMigration(from = 1, to = 2, spec = Schema1To2Migration::class),
    ],
    exportSchema = true,
)
@androidx.room.TypeConverters(TypeConverters::class)
abstract class ComposedBudgetsRoomDb : RoomDatabase() {

    abstract fun expensesDao(): ExpensesDao

    abstract fun budgetsDao(): BudgetsDao
}

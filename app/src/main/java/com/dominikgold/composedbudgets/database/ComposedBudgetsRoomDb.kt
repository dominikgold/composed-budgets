package com.dominikgold.composedbudgets.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.dominikgold.composedbudgets.database.budgets.BudgetsDao
import com.dominikgold.composedbudgets.database.budgets.PersistedBudget
import com.dominikgold.composedbudgets.database.expenses.ExpensesDao
import com.dominikgold.composedbudgets.database.expenses.PersistedExpense

@Database(entities = [PersistedBudget::class, PersistedExpense::class], version = 1, exportSchema = false)
@androidx.room.TypeConverters(TypeConverters::class)
abstract class ComposedBudgetsRoomDb : RoomDatabase() {

    abstract fun expensesDao(): ExpensesDao

    abstract fun budgetsDao(): BudgetsDao
}

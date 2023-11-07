package com.dominikgold.composedbudgets.database

import android.content.Context
import androidx.room.Room
import com.dominikgold.composedbudgets.database.budgets.BudgetsDataStore
import com.dominikgold.composedbudgets.database.budgets.RoomBudgetsDataStore
import com.dominikgold.composedbudgets.database.expenses.ExpensesDataStore
import com.dominikgold.composedbudgets.database.expenses.RoomExpensesDataStore
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.bind
import org.koin.dsl.module

val databaseModule = module {
    single { createRoomDb(androidContext()) }

    single { get<ComposedBudgetsRoomDb>().budgetsDao() }
    single { get<ComposedBudgetsRoomDb>().expensesDao() }

    factory { RoomBudgetsDataStore(get()) } bind BudgetsDataStore::class
    factory { RoomExpensesDataStore(get()) } bind ExpensesDataStore::class
}

fun createRoomDb(context: Context): ComposedBudgetsRoomDb =
    Room.databaseBuilder(context, ComposedBudgetsRoomDb::class.java, "composed_budgets_db")
        .build()

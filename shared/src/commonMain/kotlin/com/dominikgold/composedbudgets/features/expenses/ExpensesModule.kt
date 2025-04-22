package com.dominikgold.composedbudgets.features.expenses

import com.dominikgold.composedbudgets.features.expenses.add.AddExpensesViewModel
import com.dominikgold.composedbudgets.features.expenses.add.csvreader.DKBExpensesCsvReader
import com.dominikgold.composedbudgets.features.expenses.add.csvreader.ExpensesCsvReader
import org.koin.dsl.bind
import org.koin.dsl.module

val expensesModule = module {
    factory { AddExpensesViewModel(get()) }
    factory { DKBExpensesCsvReader(get()) } bind ExpensesCsvReader::class
}

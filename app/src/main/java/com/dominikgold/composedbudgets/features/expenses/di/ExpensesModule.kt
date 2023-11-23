package com.dominikgold.composedbudgets.features.expenses.di

import com.dominikgold.composedbudgets.features.expenses.add.AddExpenseHostViewModel
import com.dominikgold.composedbudgets.features.expenses.add.AddExpenseViewModel
import com.dominikgold.composedbudgets.features.expenses.usecases.AddExpense
import com.dominikgold.composedbudgets.features.expenses.usecases.DeleteExpense
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val expensesModule = module {
    factory { AddExpense(get(), get(), get()) }
    factory { DeleteExpense(get()) }
    viewModel { AddExpenseViewModel(get(), get()) }
    viewModel { AddExpenseHostViewModel() }
}

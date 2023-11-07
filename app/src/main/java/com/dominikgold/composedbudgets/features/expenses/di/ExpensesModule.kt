package com.dominikgold.composedbudgets.features.expenses.di

import com.dominikgold.composedbudgets.features.expenses.usecases.AddExpense
import com.dominikgold.composedbudgets.features.expenses.AddExpenseViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val expensesModule = module {
    factory { AddExpense(get(), get(), get()) }
    viewModel { AddExpenseViewModel(get(), get()) }
}

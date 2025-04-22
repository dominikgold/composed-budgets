package com.dominikgold.composedbudgets.android.features.expenses.di

import com.dominikgold.composedbudgets.android.features.expenses.add.AddExpensesViewModelContainer
import com.dominikgold.composedbudgets.features.expenses.expensesModule
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val androidExpensesModule = module {
    includes(expensesModule)
    viewModel { AddExpensesViewModelContainer() }
}

package com.dominikgold.composedbudgets.features.overview.di

import com.dominikgold.composedbudgets.features.overview.BudgetsOverviewViewModel
import com.dominikgold.composedbudgets.features.overview.usecases.GetCurrentExpensesInBudgets
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val budgetsOverviewModule = module {
    factory { GetCurrentExpensesInBudgets(get(), get(), get()) }
    viewModel { BudgetsOverviewViewModel(get(), get(), get()) }
}

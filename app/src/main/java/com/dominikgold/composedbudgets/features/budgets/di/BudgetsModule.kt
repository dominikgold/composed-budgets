package com.dominikgold.composedbudgets.features.budgets.di

import com.dominikgold.composedbudgets.features.budgets.EditBudgetViewModel
import com.dominikgold.composedbudgets.features.budgets.usecases.AddBudget
import com.dominikgold.composedbudgets.features.budgets.usecases.UpdateBudget
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val budgetsModule = module {
    factory { AddBudget(get(), get(), get()) }
    factory { UpdateBudget(get()) }

    viewModel { parameters -> EditBudgetViewModel(get(), get(), parameters.get(), get(), get()) }
}

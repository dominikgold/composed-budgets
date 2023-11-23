package com.dominikgold.composedbudgets.features.budgets.di

import com.dominikgold.composedbudgets.features.budgets.detail.BudgetDetailViewModel
import com.dominikgold.composedbudgets.features.budgets.edit.EditBudgetViewModel
import com.dominikgold.composedbudgets.features.budgets.usecases.AddBudget
import com.dominikgold.composedbudgets.features.budgets.usecases.DeleteBudget
import com.dominikgold.composedbudgets.features.budgets.usecases.GetAllExpensesInBudget
import com.dominikgold.composedbudgets.features.budgets.usecases.GetBudget
import com.dominikgold.composedbudgets.features.budgets.usecases.UpdateBudget
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val budgetsModule = module {
    factory { AddBudget(get(), get(), get()) }
    factory { UpdateBudget(get()) }
    factory { GetBudget(get()) }
    factory { DeleteBudget(get(), get()) }
    factory { GetAllExpensesInBudget(get()) }

    viewModel { parameters -> EditBudgetViewModel(get(), get(), get(), get(), parameters.getOrNull(), get(), get()) }
    viewModel { parameters -> BudgetDetailViewModel(parameters.get(), get(), get(), get(), get(), get()) }
}

package com.dominikgold.composedbudgets.features.home

import com.dominikgold.composedbudgets.features.expenses.GetRunningExpensesBreakdown
import org.koin.dsl.module

val homeModule = module {
    factory { GetRunningExpensesBreakdown(get(), get(), get()) }
    factory { HomeViewModel(get()) }
    factory { RunningExpensesBreakdownViewModel(get(), get()) }
}

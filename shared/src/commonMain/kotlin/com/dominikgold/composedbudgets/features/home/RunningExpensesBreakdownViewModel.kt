package com.dominikgold.composedbudgets.features.home

import com.dominikgold.composedbudgets.entities.BudgetBreakdown
import com.dominikgold.composedbudgets.features.expenses.GetRunningExpensesBreakdown
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn

class RunningExpensesBreakdownViewModel(
    getRunningExpensesBreakdown: GetRunningExpensesBreakdown,
    coroutineScope: CoroutineScope,
) {

    val budgetBreakdown = getRunningExpensesBreakdown.get()
        .stateIn(coroutineScope, SharingStarted.WhileSubscribed(), BudgetBreakdown(listOf(), listOf()))
}

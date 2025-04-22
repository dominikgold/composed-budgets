package com.dominikgold.composedbudgets.features.home

import kotlinx.coroutines.flow.StateFlow

class HomeViewModel(
    val runningExpensesBreakdownViewModel: RunningExpensesBreakdownViewModel,
) {

    val monthlySummaries: StateFlow<List<ExpensesInMonthSummary>> = TODO()

    fun onExpensesInMonthSummaryClick(summary: ExpensesInMonthSummary) {

    }
}

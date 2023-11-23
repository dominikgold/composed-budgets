package com.dominikgold.composedbudgets.features.budgets.detail

import com.dominikgold.composedbudgets.domain.entities.BudgetId

data class BudgetDetailUiState(
    val budgetId: BudgetId,
    val budgetName: String,
    val expenses: List<ExpenseListSection>,
)

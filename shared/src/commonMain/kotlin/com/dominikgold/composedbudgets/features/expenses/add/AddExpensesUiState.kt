package com.dominikgold.composedbudgets.features.expenses.add

import com.dominikgold.composedbudgets.entities.Expense
import com.dominikgold.composedbudgets.entities.MonetaryValue
import kotlinx.datetime.Instant

data class AddExpensesUiState(
    val selectedFileName: String,
    val categorizedExpenses: List<Expense>,
    val uncategorizedExpenses: List<UncategorizedExpense>,
) {

    val expensesGroupedInCategories = categorizedExpenses.groupBy { it.merchant.category }
}

data class UncategorizedExpense(
    val merchantName: String,
    val date: Instant,
    val amount: MonetaryValue,
)

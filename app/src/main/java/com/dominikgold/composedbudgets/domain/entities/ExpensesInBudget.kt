package com.dominikgold.composedbudgets.domain.entities

data class ExpensesInBudget(
    val budget: Budget,
    val expenses: List<Expense>,
    val period: BudgetPeriod,
) {

    val totalExpensedAmount = expenses.sumOf { it.amount }

    val amountLeft = budget.limit - totalExpensedAmount
}

package com.dominikgold.composedbudgets.entities

data class BudgetBreakdown(
    val allCategories: List<MerchantCategory>,
    val expenses: List<Expense>,
) {
    val totalAmount = MonetaryValue(expenses.sumOf { it.amount.amount }, Currency.Euro)

    val breakdownByCategory = allCategories.map {
        ExpensesInCategoryBreakdown(it, totalAmount, expenses.filter { expense -> expense.merchant.category == it })
    }
}

data class ExpensesInCategoryBreakdown(
    val category: MerchantCategory,
    val allCategoriesAmount: MonetaryValue,
    val expenses: List<Expense>,
) {
    val totalAmount = MonetaryValue(expenses.sumOf { it.amount.amount }, Currency.Euro)

    val percentageOfTotal = totalAmount.amount / allCategoriesAmount.amount
}

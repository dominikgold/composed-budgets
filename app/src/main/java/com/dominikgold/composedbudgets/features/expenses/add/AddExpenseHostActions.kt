package com.dominikgold.composedbudgets.features.expenses.add

import com.dominikgold.composedbudgets.domain.entities.BudgetId

interface AddExpenseHostActions {

    fun onAddExpenseClicked(toBudget: BudgetId, budgetName: String)

    fun onAddExpenseBottomSheetDismissed()
}

class AddExpenseHostActionsFake : AddExpenseHostActions {
    override fun onAddExpenseClicked(toBudget: BudgetId, budgetName: String) {}
    override fun onAddExpenseBottomSheetDismissed() {}
}

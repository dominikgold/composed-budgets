package com.dominikgold.composedbudgets.features.budgets.detail

import com.dominikgold.composedbudgets.domain.entities.ExpenseId

interface BudgetDetailActions {

    fun onEditBudgetClicked()

    fun onDeleteExpenseClicked(expenseId: ExpenseId)

    fun onUndoDeleteDismissed()

    fun onUndoDeleteClicked()

    fun onCloseClicked()
}

class BudgetDetailActionsFake : BudgetDetailActions {
    override fun onEditBudgetClicked() {}
    override fun onDeleteExpenseClicked(expenseId: ExpenseId) {}
    override fun onUndoDeleteDismissed() {}
    override fun onUndoDeleteClicked() {}
    override fun onCloseClicked() {}
}

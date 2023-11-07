package com.dominikgold.composedbudgets.features.budgets.usecases

import com.dominikgold.composedbudgets.database.budgets.BudgetsDataStore
import com.dominikgold.composedbudgets.database.expenses.ExpensesDataStore
import com.dominikgold.composedbudgets.domain.entities.BudgetId

class DeleteBudget(private val budgetsDataStore: BudgetsDataStore, private val expensesDataStore: ExpensesDataStore) {

    suspend fun delete(budgetId: BudgetId) {
        budgetsDataStore.deleteBudget(budgetId)
        expensesDataStore.deleteExpenses(forBudgetId = budgetId)
    }
}

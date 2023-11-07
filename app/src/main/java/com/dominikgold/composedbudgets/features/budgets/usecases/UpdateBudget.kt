package com.dominikgold.composedbudgets.features.budgets.usecases

import com.dominikgold.composedbudgets.database.budgets.BudgetsDataStore
import com.dominikgold.composedbudgets.domain.entities.BudgetId

class UpdateBudget(private val budgetsDataStore: BudgetsDataStore) {

    suspend fun update(budgetId: BudgetId, data: BudgetInputData) {
        budgetsDataStore.updateBudget(budgetId, data)
    }
}

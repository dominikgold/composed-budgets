package com.dominikgold.composedbudgets.features.budgets.usecases

import com.dominikgold.composedbudgets.database.budgets.BudgetsDataStore
import com.dominikgold.composedbudgets.domain.entities.Budget
import com.dominikgold.composedbudgets.domain.entities.BudgetId

class GetBudget(private val budgetsDataStore: BudgetsDataStore) {

    suspend fun get(budgetId: BudgetId): Budget? {
        return budgetsDataStore.getBudget(budgetId)
    }
}
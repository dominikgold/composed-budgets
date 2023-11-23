package com.dominikgold.composedbudgets.features.budgets.usecases

import com.dominikgold.composedbudgets.database.budgets.BudgetsDataStore
import com.dominikgold.composedbudgets.domain.entities.Budget
import com.dominikgold.composedbudgets.domain.entities.BudgetId
import kotlinx.coroutines.flow.Flow

class GetBudget(private val budgetsDataStore: BudgetsDataStore) {

    suspend fun get(budgetId: BudgetId): Budget? {
        return budgetsDataStore.getBudget(budgetId)
    }

    fun observe(budgetId: BudgetId): Flow<Budget> {
        return budgetsDataStore.observeBudget(budgetId)
    }
}
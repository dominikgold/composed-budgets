package com.dominikgold.composedbudgets.database.budgets

import com.dominikgold.composedbudgets.domain.entities.Budget
import com.dominikgold.composedbudgets.domain.entities.BudgetId
import com.dominikgold.composedbudgets.features.budgets.usecases.BudgetInputData
import kotlinx.coroutines.flow.Flow

interface BudgetsDataStore {

    fun observeBudgets(): Flow<List<Budget>>

    suspend fun getBudget(id: BudgetId): Budget?

    fun observeBudget(id: BudgetId): Flow<Budget>

    suspend fun createBudget(budget: Budget)

    suspend fun updateBudget(budgetId: BudgetId, data: BudgetInputData)

    suspend fun deleteBudget(id: BudgetId)
}

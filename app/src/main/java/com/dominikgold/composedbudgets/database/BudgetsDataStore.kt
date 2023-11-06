package com.dominikgold.composedbudgets.database

import com.dominikgold.composedbudgets.domain.entities.Budget
import com.dominikgold.composedbudgets.domain.entities.BudgetId
import kotlinx.coroutines.flow.Flow

interface BudgetsDataStore {

    fun getBudgets(): Flow<List<Budget>>

    suspend fun createBudget(budget: Budget)

    suspend fun deleteBudget(id: BudgetId)
}

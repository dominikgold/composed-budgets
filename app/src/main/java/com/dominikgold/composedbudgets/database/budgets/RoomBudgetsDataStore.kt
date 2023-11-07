package com.dominikgold.composedbudgets.database.budgets

import com.dominikgold.composedbudgets.domain.entities.Budget
import com.dominikgold.composedbudgets.domain.entities.BudgetId
import com.dominikgold.composedbudgets.features.budgets.usecases.BudgetInputData
import kotlinx.coroutines.flow.Flow

internal class RoomBudgetsDataStore(private val budgetsDao: BudgetsDao) : BudgetsDataStore {

    override fun getBudgets(): Flow<List<Budget>> {
        TODO("Not yet implemented")
    }

    override suspend fun createBudget(budget: Budget) {
        TODO("Not yet implemented")
    }

    override suspend fun updateBudget(budgetId: BudgetId, data: BudgetInputData) {
        TODO("Not yet implemented")
    }

    override suspend fun deleteBudget(id: BudgetId) {
        TODO("Not yet implemented")
    }
}

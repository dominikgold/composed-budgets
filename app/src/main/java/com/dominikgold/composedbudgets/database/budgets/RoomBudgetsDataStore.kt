package com.dominikgold.composedbudgets.database.budgets

import com.dominikgold.composedbudgets.domain.entities.Budget
import com.dominikgold.composedbudgets.domain.entities.BudgetId
import com.dominikgold.composedbudgets.features.budgets.usecases.BudgetInputData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

internal class RoomBudgetsDataStore(private val budgetsDao: BudgetsDao) : BudgetsDataStore {

    override fun getBudgets(): Flow<List<Budget>> {
        return budgetsDao.getBudgets()
            .map { persistedBudgets -> persistedBudgets.map { it.toEntity() } }
    }

    override suspend fun getBudget(id: BudgetId): Budget? {
        return budgetsDao.getBudget(id)?.toEntity()
    }

    override suspend fun createBudget(budget: Budget) {
        budgetsDao.upsertBudget(budget.toPersistedModel())
    }

    override suspend fun updateBudget(budgetId: BudgetId, data: BudgetInputData) {
        val existingBudget = budgetsDao.getBudget(budgetId) ?: error("No budget found for id $budgetId")
        val newBudget = existingBudget.copy(
            name = data.name,
            limit = data.limit,
            interval = data.interval,
            excessCarryOver = data.excessCarryOver,
            overdraftCarryOver = data.overdraftCarryOver,
        )
        budgetsDao.upsertBudget(newBudget)
    }

    override suspend fun deleteBudget(id: BudgetId) {
        budgetsDao.deleteBudget(id)
    }
}

package com.dominikgold.composedbudgets.features.budgets.usecases

import com.dominikgold.composedbudgets.common.DateTimeProvider
import com.dominikgold.composedbudgets.common.Percentage
import com.dominikgold.composedbudgets.common.UuidGenerator
import com.dominikgold.composedbudgets.database.budgets.BudgetsDataStore
import com.dominikgold.composedbudgets.domain.entities.Budget
import com.dominikgold.composedbudgets.domain.entities.BudgetId
import com.dominikgold.composedbudgets.domain.entities.BudgetInterval

class AddBudget(
    private val budgetsDataStore: BudgetsDataStore,
    private val uuidGenerator: UuidGenerator,
    private val dateTimeProvider: DateTimeProvider,
) {

    suspend fun add(data: BudgetInputData) {
        val budget = Budget(
            id = BudgetId(uuidGenerator.generate()),
            createdAt = dateTimeProvider.now(),
            name = data.name,
            limit = data.limit,
            interval = data.interval,
            excessCarryOver = data.excessCarryOver,
            overdraftCarryOver = data.overdraftCarryOver,
        )
        budgetsDataStore.createBudget(budget)
    }
}

data class BudgetInputData(
    val name: String,
    val limit: Double,
    val interval: BudgetInterval,
    val excessCarryOver: Percentage,
    val overdraftCarryOver: Percentage,
)

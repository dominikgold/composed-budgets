package com.dominikgold.composedbudgets.features.overview.usecases

import com.dominikgold.composedbudgets.common.DateTimeProvider
import com.dominikgold.composedbudgets.database.budgets.BudgetsDataStore
import com.dominikgold.composedbudgets.database.expenses.ExpensesDataStore
import com.dominikgold.composedbudgets.domain.entities.Budget
import com.dominikgold.composedbudgets.domain.entities.ExpensesInBudget
import com.dominikgold.composedbudgets.domain.entities.currentBudgetPeriod
import com.dominikgold.composedbudgets.domain.entities.endTime
import com.dominikgold.composedbudgets.domain.entities.startTime
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map

class GetCurrentExpensesInBudgets(
    private val budgetsDataStore: BudgetsDataStore,
    private val expensesDataStore: ExpensesDataStore,
    private val dateTimeProvider: DateTimeProvider,
) {

    fun get(): Flow<List<ExpensesInBudget>> {
        return budgetsDataStore.observeBudgets()
            .flatMapLatest { budgets ->
                val expensesFlows = budgets.map { budget ->
                    budget.getCurrentExpensesFlow()
                }
                combine(expensesFlows) { it.toList() }
            }
    }

    private fun Budget.getCurrentExpensesFlow(): Flow<ExpensesInBudget> {
        val budgetPeriod = this.interval.currentBudgetPeriod(dateTimeProvider.now())
        return expensesDataStore.getExpensesInPeriod(
            budgetId = this.id,
            startTime = budgetPeriod.startTime,
            endTime = budgetPeriod.endTime
        ).map { expenses ->
            ExpensesInBudget(this, expenses, budgetPeriod)
        }
    }
}

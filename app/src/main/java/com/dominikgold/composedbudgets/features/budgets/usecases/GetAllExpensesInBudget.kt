package com.dominikgold.composedbudgets.features.budgets.usecases

import com.dominikgold.composedbudgets.database.expenses.ExpensesDataStore
import com.dominikgold.composedbudgets.domain.entities.BudgetId
import com.dominikgold.composedbudgets.domain.entities.Expense
import kotlinx.coroutines.flow.Flow
import java.time.ZoneOffset
import java.time.ZonedDateTime

class GetAllExpensesInBudget(private val expensesDataStore: ExpensesDataStore) {

    fun observe(id: BudgetId): Flow<List<Expense>> {
        return expensesDataStore.getExpensesInPeriod(
            budgetId = id,
            startTime = ZonedDateTime.of(1970, 1, 1, 0, 0, 0, 0, ZoneOffset.UTC),
            endTime = ZonedDateTime.of(2270, 1, 1, 0, 0, 0, 0, ZoneOffset.UTC),
        )
    }
}

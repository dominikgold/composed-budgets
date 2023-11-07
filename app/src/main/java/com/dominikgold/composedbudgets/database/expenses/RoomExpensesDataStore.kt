package com.dominikgold.composedbudgets.database.expenses

import com.dominikgold.composedbudgets.domain.entities.BudgetId
import com.dominikgold.composedbudgets.domain.entities.Expense
import com.dominikgold.composedbudgets.domain.entities.ExpenseId
import kotlinx.coroutines.flow.Flow
import java.time.ZonedDateTime

internal class RoomExpensesDataStore(private val expensesDao: ExpensesDao) : ExpensesDataStore {

    override fun getExpensesInPeriod(
        budgetId: BudgetId,
        startTime: ZonedDateTime,
        endTime: ZonedDateTime,
    ): Flow<List<Expense>> {
        TODO("Not yet implemented")
    }

    override suspend fun createExpense(expense: Expense) {
        TODO("Not yet implemented")
    }

    override suspend fun deleteExpense(expenseId: ExpenseId) {
        TODO("Not yet implemented")
    }
}

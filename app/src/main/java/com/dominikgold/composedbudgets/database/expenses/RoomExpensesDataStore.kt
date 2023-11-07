package com.dominikgold.composedbudgets.database.expenses

import com.dominikgold.composedbudgets.domain.entities.BudgetId
import com.dominikgold.composedbudgets.domain.entities.Expense
import com.dominikgold.composedbudgets.domain.entities.ExpenseId
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.time.ZonedDateTime

internal class RoomExpensesDataStore(private val expensesDao: ExpensesDao) : ExpensesDataStore {

    override fun getExpensesInPeriod(
        budgetId: BudgetId,
        startTime: ZonedDateTime,
        endTime: ZonedDateTime,
    ): Flow<List<Expense>> {
        return expensesDao.getExpensesInPeriod(budgetId, startTime, endTime)
            .map { persistedExpenses ->
                persistedExpenses.map { it.toEntity() }
            }
    }

    override suspend fun createExpense(expense: Expense) {
        expensesDao.createExpense(expense.toPersistedModel())
    }

    override suspend fun deleteExpense(expenseId: ExpenseId) {
        expensesDao.deleteExpense(expenseId)
    }
}

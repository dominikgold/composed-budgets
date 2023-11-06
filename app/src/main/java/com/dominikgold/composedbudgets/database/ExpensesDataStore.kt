package com.dominikgold.composedbudgets.database

import com.dominikgold.composedbudgets.domain.entities.BudgetId
import com.dominikgold.composedbudgets.domain.entities.Expense
import com.dominikgold.composedbudgets.domain.entities.ExpenseId
import kotlinx.coroutines.flow.Flow
import java.time.ZonedDateTime

interface ExpensesDataStore {

    fun getExpensesInPeriod(budgetId: BudgetId, startTime: ZonedDateTime, endTime: ZonedDateTime): Flow<List<Expense>>

    suspend fun createExpense(expense: Expense)

    suspend fun deleteExpense(expenseId: ExpenseId)
}

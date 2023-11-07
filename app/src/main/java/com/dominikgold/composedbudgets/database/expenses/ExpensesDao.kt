package com.dominikgold.composedbudgets.database.expenses

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.dominikgold.composedbudgets.domain.entities.BudgetId
import com.dominikgold.composedbudgets.domain.entities.ExpenseId
import kotlinx.coroutines.flow.Flow
import java.time.ZonedDateTime

@Dao
interface ExpensesDao {

    @Query("SELECT * FROM expenses WHERE budgetId = :budgetId AND datetime(createdAt) < datetime(:endTime) AND datetime(createdAt) >= datetime(:startTime)")
    fun getExpensesInPeriod(
        budgetId: BudgetId,
        startTime: ZonedDateTime,
        endTime: ZonedDateTime,
    ): Flow<List<PersistedExpense>>

    @Query("SELECT * FROM expenses")
    suspend fun getAllExpenses(): List<PersistedExpense>

    @Insert
    suspend fun createExpense(expense: PersistedExpense)

    @Query("DELETE FROM expenses WHERE id = :expenseId")
    suspend fun deleteExpense(expenseId: ExpenseId)

    @Query("DELETE FROM expenses WHERE budgetId = :forBudgetId")
    suspend fun deleteExpenses(forBudgetId: BudgetId)
}

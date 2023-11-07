package com.dominikgold.composedbudgets.database.budgets

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.dominikgold.composedbudgets.domain.entities.BudgetId
import kotlinx.coroutines.flow.Flow

@Dao
interface BudgetsDao {

    @Query("SELECT * FROM budgets")
    fun getBudgets(): Flow<List<PersistedBudget>>

    @Query("SELECT * FROM budgets WHERE id = :id")
    suspend fun getBudget(id: BudgetId): PersistedBudget?

    @Upsert
    suspend fun upsertBudget(budget: PersistedBudget)

    @Query("DELETE FROM budgets WHERE id = :id")
    suspend fun deleteBudget(id: BudgetId)
}

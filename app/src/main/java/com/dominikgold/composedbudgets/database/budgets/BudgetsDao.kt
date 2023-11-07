package com.dominikgold.composedbudgets.database.budgets

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.dominikgold.composedbudgets.domain.entities.BudgetId
import kotlinx.coroutines.flow.Flow

@Dao
interface BudgetsDao {

    @Query("SELECT * FROM budgets")
    fun getBudgets(): Flow<List<PersistedBudget>>

    @Query("SELECT * FROM budgets WHERE id = :id")
    suspend fun getBudget(id: BudgetId): PersistedBudget

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertBudget(budget: PersistedBudget)

    @Delete
    suspend fun deleteBudget(id: BudgetId)
}

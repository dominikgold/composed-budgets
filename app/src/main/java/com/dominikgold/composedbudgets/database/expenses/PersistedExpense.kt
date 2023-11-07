package com.dominikgold.composedbudgets.database.expenses

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.dominikgold.composedbudgets.domain.entities.ExpenseId
import java.time.ZonedDateTime

@Entity(tableName = "expenses")
data class PersistedExpense(
    @PrimaryKey
    val id: ExpenseId,
    val name: String,
    val amount: Double,
    val budgetId: String,
    val createdAt: ZonedDateTime,
)

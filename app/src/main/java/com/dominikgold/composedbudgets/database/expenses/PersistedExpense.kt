package com.dominikgold.composedbudgets.database.expenses

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.dominikgold.composedbudgets.domain.entities.BudgetId
import com.dominikgold.composedbudgets.domain.entities.Expense
import com.dominikgold.composedbudgets.domain.entities.ExpenseId
import java.time.ZonedDateTime

@Entity(tableName = "expenses")
data class PersistedExpense(
    @PrimaryKey
    val id: ExpenseId,
    val name: String,
    val amount: Double,
    val budgetId: BudgetId,
    val createdAt: ZonedDateTime,
)

fun Expense.toPersistedModel() = PersistedExpense(
    id = id,
    name = name,
    amount = amount,
    budgetId = budgetId,
    createdAt = createdAt,
)

fun PersistedExpense.toEntity() = Expense(
    id = id,
    name = name,
    amount = amount,
    budgetId = budgetId,
    createdAt = createdAt,
)

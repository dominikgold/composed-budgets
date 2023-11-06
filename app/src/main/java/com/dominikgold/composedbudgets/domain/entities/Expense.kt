package com.dominikgold.composedbudgets.domain.entities

import java.time.ZonedDateTime

data class Expense(
    val id: ExpenseId,
    val name: String,
    val createdAt: ZonedDateTime,
    val budgetId: BudgetId,
    val amount: Double,
)

@JvmInline
value class ExpenseId(val value: String)

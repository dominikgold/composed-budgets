package com.dominikgold.composedbudgets.domain.entities

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.time.ZonedDateTime

data class Expense(
    val id: ExpenseId,
    val name: String,
    val createdAt: ZonedDateTime,
    val budgetId: BudgetId,
    val amount: Double,
)

@Parcelize
@JvmInline
value class ExpenseId(val value: String) : Parcelable

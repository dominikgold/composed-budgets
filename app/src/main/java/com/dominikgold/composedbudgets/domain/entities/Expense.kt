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
) {

    val formattedAmount: String by lazy {
        val amountString = amount.toString()
        val (beforeDecimal, afterDecimal) = amountString.split('.')
        if (afterDecimal.isEmpty()) {
            return@lazy "$beforeDecimal.00"
        } else if (afterDecimal.length == 1) {
            return@lazy "$beforeDecimal.${afterDecimal}0"
        }
        return@lazy "$beforeDecimal.${afterDecimal.take(2)}"
    }
}

@Parcelize
@JvmInline
value class ExpenseId(val value: String) : Parcelable

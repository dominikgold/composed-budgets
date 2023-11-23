package com.dominikgold.composedbudgets.domain.entities

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.time.ZonedDateTime

data class Budget(
    val id: BudgetId,
    val name: String,
    val interval: BudgetInterval,
    val limit: Double,
    val createdAt: ZonedDateTime,
//    val color: BudgetColor,
) {
    @Suppress("MagicNumber")
    val annualLimit = when (interval) {
        BudgetInterval.Annually -> limit
        BudgetInterval.Daily -> limit * 365
        BudgetInterval.Monthly -> limit * 12
        BudgetInterval.OneTime -> limit
    }
}

@Parcelize
@JvmInline
value class BudgetId(val value: String) : Parcelable

package com.dominikgold.composedbudgets.domain.entities

import com.dominikgold.composedbudgets.common.Percentage
import java.time.ZonedDateTime

data class Budget(
    val id: BudgetId,
    val name: String,
    val interval: BudgetInterval,
    val limit: Double,
    val excessCarryOver: Percentage,
    val overdraftCarryOver: Percentage,
    val createdAt: ZonedDateTime,
//    val color: BudgetColor,
)

@JvmInline
value class BudgetId(val value: String)

package com.dominikgold.composedbudgets.database.budgets

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.dominikgold.composedbudgets.common.Percentage
import com.dominikgold.composedbudgets.domain.entities.Budget
import com.dominikgold.composedbudgets.domain.entities.BudgetId
import com.dominikgold.composedbudgets.domain.entities.BudgetInterval
import java.time.ZonedDateTime

@Entity(tableName = "budgets")
data class PersistedBudget(
    @PrimaryKey
    val id: BudgetId,
    val name: String,
    val interval: BudgetInterval,
    val limit: Double,
    val excessCarryOver: Percentage,
    val overdraftCarryOver: Percentage,
    val createdAt: ZonedDateTime,
)

fun Budget.toPersistedModel() = PersistedBudget(
    id = id,
    name = name,
    interval = interval,
    limit = limit,
    excessCarryOver = excessCarryOver,
    overdraftCarryOver = overdraftCarryOver,
    createdAt = createdAt,
)

fun PersistedBudget.toEntity() = Budget(
    id = id,
    name = name,
    interval = interval,
    limit = limit,
    excessCarryOver = excessCarryOver,
    overdraftCarryOver = overdraftCarryOver,
    createdAt = createdAt,
)

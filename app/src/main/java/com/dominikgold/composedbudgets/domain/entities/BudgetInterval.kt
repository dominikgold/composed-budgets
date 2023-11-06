package com.dominikgold.composedbudgets.domain.entities

import androidx.annotation.StringRes
import com.dominikgold.composedbudgets.R
import java.time.ZoneId
import java.time.ZonedDateTime

sealed interface BudgetInterval {

    object Daily : BudgetInterval
    object Monthly : BudgetInterval
    object Annually : BudgetInterval
    object OneTime : BudgetInterval
}

fun BudgetInterval.currentBudgetPeriod(now: ZonedDateTime): BudgetPeriod {
    return when (this) {
        BudgetInterval.Daily -> BudgetPeriod.Day(now.withZoneSameInstant(ZoneId.systemDefault()).toLocalDate())
        BudgetInterval.Monthly -> BudgetPeriod.Month(now.month, now.year)
        BudgetInterval.Annually -> BudgetPeriod.Year(now.year)
        BudgetInterval.OneTime -> BudgetPeriod.Forever
    }
}

val BudgetInterval.name: Int
    @StringRes
    get() = when (this) {
        BudgetInterval.Annually -> R.string.budget_interval_annual
        BudgetInterval.Daily -> R.string.budget_interval_daily
        BudgetInterval.Monthly -> R.string.budget_interval_monthly
        BudgetInterval.OneTime -> R.string.budget_interval_one_time
    }

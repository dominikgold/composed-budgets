package com.dominikgold.composedbudgets.domain.entities

import com.dominikgold.composedbudgets.common.toLocalDateWithZone
import java.time.LocalDate
import java.time.ZoneId
import java.time.ZoneOffset
import java.time.ZonedDateTime
import java.time.temporal.TemporalAdjusters
import kotlin.math.absoluteValue

sealed interface BudgetPeriod {

    data class Day(val value: LocalDate) : BudgetPeriod

    data class Month(val month: java.time.Month, val year: Int) : BudgetPeriod

    data class Year(val year: Int) : BudgetPeriod

    object Forever : BudgetPeriod
}

private const val HOURS_IN_DAY = 24L

// TODO test date conversions in detail here

val BudgetPeriod.startTime: ZonedDateTime
    get() = when (this) {
        is BudgetPeriod.Day -> this.value.atStartOfDay(ZoneId.systemDefault()).withZoneSameInstant(ZoneOffset.UTC)
        is BudgetPeriod.Month -> ZonedDateTime.of(this.year, this.month.value, 1, 0, 0, 0, 0, ZoneOffset.UTC)
        is BudgetPeriod.Year -> ZonedDateTime.of(this.year, 1, 1, 0, 0, 0, 0, ZoneOffset.UTC)
        is BudgetPeriod.Forever -> ZonedDateTime.of(1970, 1, 1, 0, 0, 0, 0, ZoneOffset.UTC)
    }

val BudgetPeriod.endTime: ZonedDateTime
    get() = when (this) {
        is BudgetPeriod.Day -> this.startTime.plusHours(HOURS_IN_DAY)
        is BudgetPeriod.Month -> LocalDate.of(this.year, this.month.value, 1)
            .with(TemporalAdjusters.lastDayOfMonth())
            .atStartOfDay(ZoneOffset.UTC).plusHours(HOURS_IN_DAY)

        is BudgetPeriod.Year -> LocalDate.of(this.year, 1, 1)
            .with(TemporalAdjusters.lastDayOfYear())
            .atStartOfDay(ZoneOffset.UTC).plusHours(HOURS_IN_DAY)

        is BudgetPeriod.Forever -> ZonedDateTime.of(3000, 1, 1, 0, 0, 0, 0, ZoneOffset.UTC)
    }

fun ZonedDateTime.toCorrespondingBudgetPeriod(budgetInterval: BudgetInterval) = when (budgetInterval) {
    BudgetInterval.Annually -> BudgetPeriod.Year(this.year)
    BudgetInterval.Daily -> BudgetPeriod.Day(this.toLocalDateWithZone())
    BudgetInterval.Monthly -> BudgetPeriod.Month(this.month, this.year)
    BudgetInterval.OneTime -> BudgetPeriod.Forever
}

fun BudgetPeriod.Month.minus(months: Int): BudgetPeriod.Month {
    val currentMonth = this.month.value
    val difference = currentMonth - months
    val yearsToSubtract = if (difference > 0) {
        0
    } else {
        difference.absoluteValue / 12 + 1
    }
    return BudgetPeriod.Month(this.month.minus(months.toLong()), this.year - yearsToSubtract)
}

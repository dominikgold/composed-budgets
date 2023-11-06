package com.dominikgold.composedbudgets.domain.entities

import java.time.LocalDate
import java.time.ZoneId
import java.time.ZoneOffset
import java.time.ZonedDateTime
import java.time.temporal.TemporalAdjusters

sealed interface BudgetPeriod {

    data class Day(val value: LocalDate) : BudgetPeriod

    data class Month(val month: java.time.Month, val year: Int) : BudgetPeriod

    data class Year(val year: Int) : BudgetPeriod

    object Forever : BudgetPeriod
}

private const val HOURS_IN_DAY = 24L

val BudgetPeriod.startTime: ZonedDateTime
    get() = when (this) {
        is BudgetPeriod.Day -> this.value.atStartOfDay(ZoneId.systemDefault())
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

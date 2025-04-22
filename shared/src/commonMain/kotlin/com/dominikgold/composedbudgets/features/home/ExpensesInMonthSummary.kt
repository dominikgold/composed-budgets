package com.dominikgold.composedbudgets.features.home

import com.dominikgold.composedbudgets.entities.MonetaryValue
import com.dominikgold.composedbudgets.entities.MonthAndYear

data class ExpensesInMonthSummary(
    val month: MonthAndYear,
    val totalExpensesAmount: MonetaryValue,
)

fun MonthAndYear.format() = "${month.name} $year"

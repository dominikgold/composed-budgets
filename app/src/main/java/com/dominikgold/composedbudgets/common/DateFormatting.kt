package com.dominikgold.composedbudgets.common

import java.time.ZonedDateTime

fun ZonedDateTime.formatShortDate(): String {
    val localDate = this.toLocalDate()
    return "${localDate.dayOfMonth}/${localDate.monthValue}/${localDate.year}"
}

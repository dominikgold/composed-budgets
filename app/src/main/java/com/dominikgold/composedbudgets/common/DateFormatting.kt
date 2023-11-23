package com.dominikgold.composedbudgets.common

import java.time.LocalDate
import java.time.ZonedDateTime

fun ZonedDateTime.formatShortDate(): String {
    return this.toLocalDate().formatShortDate()
}

fun LocalDate.formatShortDate(): String {
    return "${this.dayOfMonth}/${this.monthValue}/${this.year}"
}

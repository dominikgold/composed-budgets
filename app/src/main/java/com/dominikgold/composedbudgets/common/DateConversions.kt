package com.dominikgold.composedbudgets.common

import java.time.ZoneId
import java.time.ZonedDateTime

fun ZonedDateTime.toLocalDateWithZone() = this.withZoneSameInstant(ZoneId.systemDefault()).toLocalDate()

fun ZonedDateTime.toLocalDateTimeWithZone() = this.withZoneSameInstant(ZoneId.systemDefault()).toLocalDateTime()

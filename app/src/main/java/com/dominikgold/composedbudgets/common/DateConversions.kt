package com.dominikgold.composedbudgets.common

import java.time.ZoneId
import java.time.ZonedDateTime

fun ZonedDateTime.toLocalDate() = this.withZoneSameInstant(ZoneId.systemDefault()).toLocalDate()

fun ZonedDateTime.toLocalDateTime() = this.withZoneSameInstant(ZoneId.systemDefault()).toLocalDateTime()

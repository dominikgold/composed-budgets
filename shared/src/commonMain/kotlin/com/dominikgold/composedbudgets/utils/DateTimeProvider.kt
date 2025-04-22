package com.dominikgold.composedbudgets.utils

import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

interface DateTimeProvider {

    fun now(): Instant

    fun today(): LocalDate
}

class DefaultDateTimeProvider : DateTimeProvider {

    override fun now(): Instant {
        return Clock.System.now()
    }

    override fun today(): LocalDate {
        val now = now()
        return now.toLocalDateTime(TimeZone.currentSystemDefault()).date
    }
}

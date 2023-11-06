package com.dominikgold.composedbudgets.common

import java.time.LocalDate
import java.time.ZonedDateTime

interface DateTimeProvider {

    fun today(): LocalDate

    fun now(): ZonedDateTime
}

class DefaultDateTimeProvider : DateTimeProvider {

    override fun today(): LocalDate {
        return LocalDate.now()
    }

    override fun now(): ZonedDateTime {
        return ZonedDateTime.now()
    }
}

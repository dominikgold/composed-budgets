package com.dominikgold.composedbudgets.database

import app.cash.sqldelight.ColumnAdapter
import com.dominikgold.composedbudgets.entities.RecurringExpensePeriod
import kotlinx.datetime.Instant

val instantAdapter = object : ColumnAdapter<Instant, String> {
    override fun decode(databaseValue: String): Instant {
        return Instant.parse(databaseValue)
    }

    override fun encode(value: Instant): String {
        return value.toString()
    }
}

val recurringExpensePeriodAdapter = object : ColumnAdapter<RecurringExpensePeriod, String> {
    override fun decode(databaseValue: String): RecurringExpensePeriod {
        return RecurringExpensePeriod.valueOf(databaseValue)
    }

    override fun encode(value: RecurringExpensePeriod): String {
        return value.name
    }
}

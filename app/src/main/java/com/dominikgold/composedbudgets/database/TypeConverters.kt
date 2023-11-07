package com.dominikgold.composedbudgets.database

import androidx.room.TypeConverter
import com.dominikgold.composedbudgets.domain.entities.BudgetInterval
import java.time.ZoneOffset
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

private val zonedDateTimeFormatter = DateTimeFormatter.ISO_ZONED_DATE_TIME

class TypeConverters {

    @TypeConverter
    fun fromZonedDateTime(date: ZonedDateTime?): String? = date?.withZoneSameInstant(ZoneOffset.UTC)?.format(zonedDateTimeFormatter)

    @TypeConverter
    fun toZonedDateTime(date: String?): ZonedDateTime? = date?.let {
        zonedDateTimeFormatter.parse(it, ZonedDateTime::from)
    }

    @TypeConverter
    fun fromBudgetInterval(interval: BudgetInterval): String = when (interval) {
        BudgetInterval.Annually -> "annually"
        BudgetInterval.Daily -> "daily"
        BudgetInterval.Monthly -> "monthly"
        BudgetInterval.OneTime -> "one-time"
    }

    @TypeConverter
    fun toBudgetInterval(interval: String): BudgetInterval = when (interval) {
        "annually" -> BudgetInterval.Annually
        "daily" -> BudgetInterval.Daily
        "monthly" -> BudgetInterval.Monthly
        "one-time" -> BudgetInterval.OneTime
        else -> error("Invalid budget interval: $interval")
    }
}

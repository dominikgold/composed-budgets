package com.dominikgold.composedbudgets.entities

import kotlin.math.absoluteValue
import kotlin.math.roundToLong

@Suppress("MagicNumber")
data class MonetaryValue(
    val amount: Long,
    val currency: Currency,
) {

    fun format(): String {
        return "${currency.sign}${toAmountString()}"
    }

    companion object {
        fun fromAmountString(amount: String): MonetaryValue? {
            if (
                amount.isEmpty() ||
                amount.count { it == '.' } > 1 ||
                amount.substringAfter(".", missingDelimiterValue = "").length > 2
            ) {
                return null
            }

            val normalizedAmount = amount.removeSuffix(".")
            val amountInCents = normalizedAmount.toDoubleOrNull()?.times(100)?.roundToLong()
            return amountInCents?.let {
                MonetaryValue(amountInCents, Currency.Euro)
            }
        }

        val ZERO = MonetaryValue(0L, Currency.Euro)
    }

    fun toAmountString(): String {
        val valueInDollars = (amount / 100).absoluteValue
        val cents = (amount % 100).absoluteValue
        return if (cents == 0L) {
            "$valueInDollars"
        } else {
            "$valueInDollars.${cents.toString().padStart(2, '0')}"
        }
    }
}

enum class Currency(val sign: String) {
    Euro("€"),
}

infix operator fun MonetaryValue.minus(other: MonetaryValue) = if (this.currency != other.currency) {
    error("Can not subtract monetary value with a different currency")
} else {
    MonetaryValue(this.amount - other.amount, this.currency)
}

fun MonetaryValue.abs() = MonetaryValue(this.amount.absoluteValue, this.currency)

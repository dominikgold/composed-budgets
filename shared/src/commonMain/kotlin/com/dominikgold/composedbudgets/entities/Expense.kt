package com.dominikgold.composedbudgets.entities

import kotlinx.datetime.Clock
import kotlinx.datetime.Instant

data class Expense(
    val id: String,
    val merchant: Merchant,
    val amount: MonetaryValue,
    val date: Instant,
    val recurringPeriod: RecurringExpensePeriod?,
) {

    companion object {
        val preview = Expense(
            "1",
            Merchant("1", "Merch123Groc", "Grocery Store", ExpenseCategory.preview),
            amount = MonetaryValue(2000, Currency.Euro),
            date = Clock.System.now(),
            recurringPeriod = null,
        )
    }
}

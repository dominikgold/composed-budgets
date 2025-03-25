package com.dominikgold.composedbudgets.entities

data class Expense(
    val id: String,
    val merchant: Merchant,
    val date: ZonedDateTime,
)

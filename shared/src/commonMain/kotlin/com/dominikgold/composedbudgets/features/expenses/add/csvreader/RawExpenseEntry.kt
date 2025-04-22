package com.dominikgold.composedbudgets.features.expenses.add.csvreader

data class RawExpenseEntry(
    val merchantName: String,
    val amount: String,
    val date: String,
)

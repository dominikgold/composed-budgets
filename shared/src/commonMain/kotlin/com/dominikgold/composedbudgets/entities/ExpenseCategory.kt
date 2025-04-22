package com.dominikgold.composedbudgets.entities

data class ExpenseCategory(
    val id: String,
    val name: String,
) {
    companion object {
        val preview = ExpenseCategory("1", "Food")
    }
}

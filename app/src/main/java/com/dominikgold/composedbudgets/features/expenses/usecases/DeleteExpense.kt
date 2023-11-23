package com.dominikgold.composedbudgets.features.expenses.usecases

import com.dominikgold.composedbudgets.database.expenses.ExpensesDataStore
import com.dominikgold.composedbudgets.domain.entities.ExpenseId

class DeleteExpense(private val expensesDataStore: ExpensesDataStore) {

    suspend fun delete(expenseId: ExpenseId) {
        expensesDataStore.deleteExpense(expenseId)
    }
}

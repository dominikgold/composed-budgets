package com.dominikgold.composedbudgets.features.expenses.usecases

import com.dominikgold.composedbudgets.common.DateTimeProvider
import com.dominikgold.composedbudgets.common.UuidGenerator
import com.dominikgold.composedbudgets.database.expenses.ExpensesDataStore
import com.dominikgold.composedbudgets.domain.entities.BudgetId
import com.dominikgold.composedbudgets.domain.entities.Expense
import com.dominikgold.composedbudgets.domain.entities.ExpenseId

class AddExpense(
    private val expensesDataStore: ExpensesDataStore,
    private val uuidGenerator: UuidGenerator,
    private val dateTimeProvider: DateTimeProvider,
) {

    suspend fun add(forBudget: BudgetId, amount: Double, name: String) {
        expensesDataStore.createExpense(Expense(ExpenseId(uuidGenerator.generate()), name, dateTimeProvider.now(), forBudget, amount))
    }
}

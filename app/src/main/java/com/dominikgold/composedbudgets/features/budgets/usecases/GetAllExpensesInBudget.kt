package com.dominikgold.composedbudgets.features.budgets.usecases

import com.dominikgold.composedbudgets.database.expenses.ExpensesDataStore
import com.dominikgold.composedbudgets.domain.entities.BudgetId
import com.dominikgold.composedbudgets.domain.entities.Expense
import kotlinx.coroutines.flow.Flow

class GetAllExpensesInBudget(private val expensesDataStore: ExpensesDataStore) {

    fun observe(id: BudgetId): Flow<List<Expense>> {
        TODO()
    }
}

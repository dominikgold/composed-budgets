package com.dominikgold.composedbudgets.database

import com.dominikgold.composedbudgets.entities.Expense
import com.dominikgold.composedbudgets.entities.ExpenseCategory
import com.dominikgold.composedbudgets.entities.RecurringExpensePeriod
import kotlinx.coroutines.flow.Flow
import kotlinx.datetime.LocalDate

interface ExpensesDataStore {

    fun observeExpensesInMonth(month: LocalDate): Flow<List<Expense>>

    fun observeExpensesInTimeRange(from: LocalDate, until: LocalDate): Flow<List<Expense>>

    fun saveExpenses(expenses: List<Expense>)

    fun getAllExpensesInCategory(category: ExpenseCategory): Flow<List<Expense>>

    fun updateRecurringPeriod(expenseId: String, period: RecurringExpensePeriod?): Flow<List<Expense>>
}

class DefaultExpensesDataStore(private val database: AppDatabase): ExpensesDataStore {

    override fun observeExpensesInMonth(month: LocalDate): Flow<List<Expense>> {
        TODO()
    }

    override fun observeExpensesInTimeRange(from: LocalDate, until: LocalDate): Flow<List<Expense>> {
        TODO()
    }

    override fun saveExpenses(expenses: List<Expense>) {
        TODO()
    }

    override fun getAllExpensesInCategory(category: ExpenseCategory): Flow<List<Expense>> {
        TODO()
    }

    override fun updateRecurringPeriod(expenseId: String, period: RecurringExpensePeriod?): Flow<List<Expense>> {
        TODO()
    }
}

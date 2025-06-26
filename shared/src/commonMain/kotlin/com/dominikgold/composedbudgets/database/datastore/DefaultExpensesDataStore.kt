package com.dominikgold.composedbudgets.database.datastore

import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToList
import com.dominikgold.composedbudgets.database.AppDatabase
import com.dominikgold.composedbudgets.entities.Expense
import com.dominikgold.composedbudgets.entities.MerchantCategory
import com.dominikgold.composedbudgets.entities.MonetaryValue
import com.dominikgold.composedbudgets.entities.RecurringExpensePeriod
import com.dominikgold.composedbudgets.utils.coroutines.CoroutineDispatcherProvider
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.datetime.LocalDate

interface ExpensesDataStore {

    fun observeExpensesInMonth(month: LocalDate): Flow<List<Expense>>

    fun observeExpensesInTimeRange(from: LocalDate, until: LocalDate): Flow<List<Expense>>

    fun saveExpenses(expenses: List<Expense>)

    fun getAllExpensesInCategory(category: MerchantCategory): Flow<List<Expense>>

    fun updateRecurringPeriod(expenseId: String, period: RecurringExpensePeriod?): Flow<List<Expense>>
}

class DefaultExpensesDataStore(
    private val database: AppDatabase,
    private val dispatcherProvider: CoroutineDispatcherProvider,
    private val merchantDataStore: MerchantDataStore,
) : ExpensesDataStore {

    override fun observeExpensesInMonth(month: LocalDate): Flow<List<Expense>> {
        return database.instance.composedBudgetsDatabaseQueries.getAllExpenses()
            .asFlow()
            .mapToList(dispatcherProvider.io())
            .combine(merchantDataStore.getAllMerchants()) { allExpenses, merchants ->
                allExpenses.map { dbExpense ->
                    Expense(
                        id = dbExpense.id,
                        amount = MonetaryValue(dbExpense.amount_value.toLong(), dbExpense.amount_currency),
                        date = dbExpense.date,
                        merchant = merchants.find { it.id == dbExpense.merchant_id },
                        recurringPeriod = dbExpense.recurringPeriod?.let { RecurringExpensePeriod.valueOf(it) }
                    )
                }
            }
    }

    override fun observeExpensesInTimeRange(from: LocalDate, until: LocalDate): Flow<List<Expense>> {
        TODO()
    }

    override fun saveExpenses(expenses: List<Expense>) {
        TODO()
    }

    override fun getAllExpensesInCategory(category: MerchantCategory): Flow<List<Expense>> {
        TODO()
    }

    override fun updateRecurringPeriod(expenseId: String, period: RecurringExpensePeriod?): Flow<List<Expense>> {
        TODO()
    }
}

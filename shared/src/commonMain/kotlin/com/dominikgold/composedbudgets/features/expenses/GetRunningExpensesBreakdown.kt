package com.dominikgold.composedbudgets.features.expenses

import com.dominikgold.composedbudgets.database.ExpensesDataStore
import com.dominikgold.composedbudgets.database.MerchantDataStore
import com.dominikgold.composedbudgets.entities.BudgetBreakdown
import com.dominikgold.composedbudgets.utils.DateTimeProvider
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.minus

private const val RUNNING_EXPENSES_NUMBER_OF_MONTHS = 12

class GetRunningExpensesBreakdown(
    private val dateTimeProvider: DateTimeProvider,
    private val merchantDataStore: MerchantDataStore,
    private val expensesDataStore: ExpensesDataStore,
) {

    fun get(): Flow<BudgetBreakdown> {
        val today = dateTimeProvider.today()
        val includeExpensesFrom = today.minus(RUNNING_EXPENSES_NUMBER_OF_MONTHS, DateTimeUnit.MONTH)
        return expensesDataStore.observeExpensesInTimeRange(from = includeExpensesFrom, until = today)
            .combine(merchantDataStore.getAllCategories()) { expenses, categories ->
                BudgetBreakdown(allCategories = categories, expenses = expenses)
            }
    }
}

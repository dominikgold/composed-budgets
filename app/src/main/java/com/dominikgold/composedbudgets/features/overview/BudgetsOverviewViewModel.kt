package com.dominikgold.composedbudgets.features.overview

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dominikgold.composedbudgets.common.DateTimeProvider
import com.dominikgold.composedbudgets.domain.entities.BudgetId
import com.dominikgold.composedbudgets.features.expenses.AddExpenseBottomSheetData
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

class BudgetsOverviewViewModel(
    getCurrentExpensesInBudgets: GetCurrentExpensesInBudgets,
    private val dateTimeProvider: DateTimeProvider,
) : ViewModel() {

    val addExpenseBottomSheetData = MutableStateFlow<AddExpenseBottomSheetData?>(null)

    val listItems: StateFlow<List<BudgetsOverviewListItem>> = getCurrentExpensesInBudgets.get()
        .map { expensesInBudgets ->
            val currentTime = dateTimeProvider.now()
            expensesInBudgets.map { BudgetsOverviewListItem(it, currentTime) }
        }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), listOf())

    fun onAddExpenseClicked(listItem: BudgetsOverviewListItem) {
        addExpenseBottomSheetData.value = AddExpenseBottomSheetData(listItem.budgetId, listItem.name)
    }

    fun onAddExpenseBottomSheetDismissed() {
        addExpenseBottomSheetData.value = null
    }

    fun onBudgetClicked(budgetId: BudgetId) {
        TODO("navigation")
    }
}

package com.dominikgold.composedbudgets.features.overview

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dominikgold.composedbudgets.common.DateTimeProvider
import com.dominikgold.composedbudgets.domain.entities.BudgetId
import com.dominikgold.composedbudgets.features.overview.usecases.GetCurrentExpensesInBudgets
import com.dominikgold.composedbudgets.navigation.Destination
import com.dominikgold.composedbudgets.navigation.Navigator
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

class BudgetsOverviewViewModel(
    getCurrentExpensesInBudgets: GetCurrentExpensesInBudgets,
    private val navigator: Navigator,
    private val dateTimeProvider: DateTimeProvider,
) : ViewModel() {

    val listItems: StateFlow<List<BudgetsOverviewListItem>> = getCurrentExpensesInBudgets.get()
        .map { expensesInBudgets ->
            val currentTime = dateTimeProvider.now()
            expensesInBudgets.map { BudgetsOverviewListItem(it, currentTime) }
        }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), listOf())

    fun onBudgetClicked(budgetId: BudgetId) {
        navigator.navigateTo(Destination.BudgetDetail(budgetId))
    }

    fun onAddBudgetClicked() {
        navigator.navigateTo(Destination.EditBudget(null))
    }
}

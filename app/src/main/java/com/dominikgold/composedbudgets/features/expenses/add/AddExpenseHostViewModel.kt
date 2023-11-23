package com.dominikgold.composedbudgets.features.expenses.add

import androidx.lifecycle.ViewModel
import com.dominikgold.composedbudgets.domain.entities.BudgetId
import kotlinx.coroutines.flow.MutableStateFlow

class AddExpenseHostViewModel : ViewModel(), AddExpenseHostActions {

    val addExpenseBottomSheetData = MutableStateFlow<AddExpenseBottomSheetData?>(null)

    override fun onAddExpenseClicked(toBudget: BudgetId, budgetName: String) {
        addExpenseBottomSheetData.value = AddExpenseBottomSheetData(toBudget, budgetName)
    }

    override fun onAddExpenseBottomSheetDismissed() {
        addExpenseBottomSheetData.value = null
    }
}

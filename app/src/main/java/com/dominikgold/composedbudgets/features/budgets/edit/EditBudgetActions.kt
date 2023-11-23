package com.dominikgold.composedbudgets.features.budgets.edit

import com.dominikgold.composedbudgets.domain.entities.BudgetInterval

interface EditBudgetActions {

    fun onNameInputChanged(input: String)

    fun onLimitInputChanged(input: String)

    fun onIntervalChanged(interval: BudgetInterval)

    fun onChangeIntervalClicked()

    fun onChangeIntervalSheetDismissed()

    fun onCloseClicked()

    fun onSaveClicked()

    fun onDeleteBudgetClicked()
}

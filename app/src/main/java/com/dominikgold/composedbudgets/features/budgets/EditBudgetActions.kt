package com.dominikgold.composedbudgets.features.budgets

import com.dominikgold.composedbudgets.common.Percentage
import com.dominikgold.composedbudgets.domain.entities.BudgetInterval

interface EditBudgetActions {

    fun onNameInputChanged(input: String)

    fun onLimitInputChanged(input: String)

    fun onIntervalChanged(interval: BudgetInterval)

    fun onChangeIntervalClicked()

    fun onChangeIntervalSheetDismissed()

    fun onExcessCarryOverInputChanged(percentage: Percentage)

    fun onOverdraftCarryOverInputChanged(percentage: Percentage)

    fun onCloseClicked()

    fun onSaveClicked()
}

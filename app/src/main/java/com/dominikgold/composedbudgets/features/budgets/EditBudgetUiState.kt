package com.dominikgold.composedbudgets.features.budgets

import com.dominikgold.composedbudgets.common.Percentage
import com.dominikgold.composedbudgets.common.parseUserInputToDouble
import com.dominikgold.composedbudgets.domain.entities.BudgetInterval

data class EditBudgetUiState(
    val currentLimitInput: String,
    val currentNameInput: String,
    val currentExcessCarryOver: Percentage,
    val currentOverdraftCarryOver: Percentage,
    val currentInterval: BudgetInterval,
) {

    val isSaveButtonEnabled = currentNameInput.isNotEmpty() && currentLimitInput.parseUserInputToDouble() != null
}

package com.dominikgold.composedbudgets.features.budgets.edit

import com.dominikgold.composedbudgets.common.parseUserInputToDouble
import com.dominikgold.composedbudgets.domain.entities.BudgetInterval

data class EditBudgetUiState(
    val currentLimitInput: String,
    val currentNameInput: String,
    val currentInterval: BudgetInterval,
) {

    val isSaveButtonEnabled = currentNameInput.isNotEmpty() && currentLimitInput.parseUserInputToDouble() != null
}

package com.dominikgold.composedbudgets.features.expenses

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dominikgold.composedbudgets.common.UiEventsFlow
import com.dominikgold.composedbudgets.common.parseUserInputToDouble
import com.dominikgold.composedbudgets.domain.entities.BudgetId
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

private const val AMOUNT_INPUT_KEY = "amount_input_key"
private const val NAME_INPUT_KEY = "name_input_key"

class AddExpenseViewModel(
    private val addExpense: AddExpense,
    private val savedStateHandle: SavedStateHandle,
) : ViewModel() {

    val dismissSheetEvents = UiEventsFlow<Unit>()

    private var budgetId: BudgetId? = null

    val currentAmountInput: StateFlow<String> = savedStateHandle.getStateFlow(AMOUNT_INPUT_KEY, "")
    val currentNameInput: StateFlow<String> = savedStateHandle.getStateFlow(NAME_INPUT_KEY, "")

    fun setBudgetData(id: BudgetId) {
        this.budgetId = id
    }

    fun onDoneClicked() {
        val budgetId = this.budgetId ?: return
        val amountInput = currentAmountInput.value.parseUserInputToDouble()
        if (amountInput == null) {
            dismissSheetEvents.tryEmit(Unit)
            return
        }

        viewModelScope.launch {
            addExpense.add(budgetId, amountInput, currentAmountInput.value)
            dismissSheetEvents.emit(Unit)
        }
    }

    fun onAmountInputChanged(input: String) {
        if (input.parseUserInputToDouble() != null) {
            savedStateHandle[AMOUNT_INPUT_KEY] = input
        }
    }

    fun onNameInputChanged(input: String) {
        savedStateHandle[NAME_INPUT_KEY] = input
    }
}

package com.dominikgold.composedbudgets.features.budgets

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dominikgold.composedbudgets.common.Percentage
import com.dominikgold.composedbudgets.common.parseUserInputToDouble
import com.dominikgold.composedbudgets.domain.entities.BudgetId
import com.dominikgold.composedbudgets.domain.entities.BudgetInterval
import com.dominikgold.composedbudgets.features.budgets.usecases.AddBudget
import com.dominikgold.composedbudgets.features.budgets.usecases.BudgetInputData
import com.dominikgold.composedbudgets.features.budgets.usecases.GetBudget
import com.dominikgold.composedbudgets.features.budgets.usecases.UpdateBudget
import com.dominikgold.composedbudgets.navigation.Navigator
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

private const val LIMIT_INPUT_KEY = "limit_input_key"
private const val NAME_INPUT_KEY = "name_input_key"
private const val EXCESS_CARRY_OVER_INPUT_KEY = "excess_input_key"
private const val OVERDRAFT_CARRY_OVER_INPUT_KEY = "overdraft_input_key"
private const val INTERVAL_INPUT_KEY = "interval_input_key"

class EditBudgetViewModel(
    private val addBudget: AddBudget,
    private val updateBudget: UpdateBudget,
    private val getBudget: GetBudget,
    private val budgetId: BudgetId?,
    private val navigator: Navigator,
    private val savedStateHandle: SavedStateHandle,
) : ViewModel(), EditBudgetActions {

    val isEditMode = budgetId != null

    private val currentLimitInput: StateFlow<String> = savedStateHandle.getStateFlow(LIMIT_INPUT_KEY, "")
    private val currentNameInput: StateFlow<String> = savedStateHandle.getStateFlow(NAME_INPUT_KEY, "")
    private val currentExcessCarryOver: StateFlow<Percentage> = savedStateHandle.getStateFlow(EXCESS_CARRY_OVER_INPUT_KEY, Percentage(1f))
    private val currentOverdraftCarryOver: StateFlow<Percentage> =
        savedStateHandle.getStateFlow(OVERDRAFT_CARRY_OVER_INPUT_KEY, Percentage(1f))
    private val currentInterval: StateFlow<BudgetInterval> = savedStateHandle.getStateFlow(INTERVAL_INPUT_KEY, BudgetInterval.Monthly)

    val changeIntervalSheet = MutableStateFlow<ChangeBudgetIntervalBottomSheetData?>(null)

    val uiState = combine(
        currentLimitInput,
        currentNameInput,
        currentExcessCarryOver,
        currentOverdraftCarryOver,
        currentInterval,
    ) { currentLimitInput, currentNameInput, currentExcessCarryOver, currentOverdraftCarryOver, currentInterval ->
        EditBudgetUiState(currentLimitInput, currentNameInput, currentExcessCarryOver, currentOverdraftCarryOver, currentInterval)
    }.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(),
        EditBudgetUiState("", "", Percentage(1f), Percentage(1f), BudgetInterval.Monthly)
    )

    init {
        if (budgetId != null) {
            viewModelScope.launch {
                getBudget.get(budgetId)?.let {
                    savedStateHandle[NAME_INPUT_KEY] = it.name
                    savedStateHandle[LIMIT_INPUT_KEY] = it.limit.toString()
                    savedStateHandle[INTERVAL_INPUT_KEY] = it.interval
                    savedStateHandle[EXCESS_CARRY_OVER_INPUT_KEY] = it.excessCarryOver
                    savedStateHandle[OVERDRAFT_CARRY_OVER_INPUT_KEY] = it.overdraftCarryOver
                }
            }
        }
    }

    override fun onNameInputChanged(input: String) {
        savedStateHandle[NAME_INPUT_KEY] = input
    }

    override fun onLimitInputChanged(input: String) {
        if (input.isEmpty() || input.parseUserInputToDouble() != null) {
            savedStateHandle[LIMIT_INPUT_KEY] = input
        }
    }

    override fun onIntervalChanged(interval: BudgetInterval) {
        savedStateHandle[INTERVAL_INPUT_KEY] = interval
        changeIntervalSheet.value = null
    }

    override fun onChangeIntervalClicked() {
        changeIntervalSheet.value = ChangeBudgetIntervalBottomSheetData(currentInterval.value)
    }

    override fun onChangeIntervalSheetDismissed() {
        changeIntervalSheet.value = null
    }

    override fun onExcessCarryOverInputChanged(percentage: Percentage) {
        savedStateHandle[EXCESS_CARRY_OVER_INPUT_KEY] = percentage
    }

    override fun onOverdraftCarryOverInputChanged(percentage: Percentage) {
        savedStateHandle[OVERDRAFT_CARRY_OVER_INPUT_KEY] = percentage
    }

    override fun onCloseClicked() {
        navigator.goBack()
    }

    override fun onSaveClicked() {
        val limitInput = currentLimitInput.value.parseUserInputToDouble()
        if (!uiState.value.isSaveButtonEnabled || limitInput == null) {
            return
        }

        viewModelScope.launch {
            val inputData = BudgetInputData(
                name = currentNameInput.value,
                limit = limitInput,
                interval = currentInterval.value,
                excessCarryOver = currentExcessCarryOver.value,
                overdraftCarryOver = currentOverdraftCarryOver.value,
            )
            if (budgetId != null) {
                updateBudget.update(budgetId, inputData)
            } else {
                addBudget.add(inputData)
            }
            navigator.goBack()
        }
    }
}

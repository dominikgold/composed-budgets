package com.dominikgold.composedbudgets.features.budgets

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material.icons.rounded.KeyboardArrowDown
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.dominikgold.composedbudgets.R
import com.dominikgold.composedbudgets.common.Percentage
import com.dominikgold.composedbudgets.domain.entities.BudgetId
import com.dominikgold.composedbudgets.domain.entities.BudgetInterval
import com.dominikgold.composedbudgets.domain.entities.name
import com.dominikgold.composedbudgets.ui.theme.ComposedBudgetsTheme
import org.koin.androidx.compose.koinViewModel
import org.koin.core.parameter.parametersOf

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditBudgetUi(budgetId: BudgetId?) {
    val viewModel = koinViewModel<EditBudgetViewModel>(parameters = { parametersOf(budgetId) })

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val changeIntervalBottomSheet by viewModel.changeIntervalSheet.collectAsStateWithLifecycle()
    val bottomSheetState = rememberModalBottomSheetState()
    val focusManager = LocalFocusManager.current

    LaunchedEffect(key1 = changeIntervalBottomSheet) {
        if (changeIntervalBottomSheet == null) {
            focusManager.clearFocus()
        }
    }

    EditBudgetContent(uiState, viewModel.isEditMode, viewModel)

    changeIntervalBottomSheet?.let {
        ModalBottomSheet(
            onDismissRequest = viewModel::onChangeIntervalSheetDismissed,
            sheetState = bottomSheetState,
        ) {
            ChangeBudgetIntervalBottomSheetUi(
                data = it,
                onIntervalSelected = viewModel::onIntervalChanged,
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditBudgetContent(uiState: EditBudgetUiState, isEditMode: Boolean, actions: EditBudgetActions) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = if (isEditMode) {
                            stringResource(id = R.string.add_budget_title)
                        } else {
                            stringResource(id = R.string.edit_budget_title)
                        },
                    )
                },
                navigationIcon = {
                    IconButton(onClick = actions::onCloseClicked) {
                        Icon(Icons.Rounded.Close, contentDescription = "Close")
                    }
                },
                actions = {
                    TextButton(onClick = actions::onSaveClicked, enabled = uiState.isSaveButtonEnabled) {
                        Text(text = stringResource(id = R.string.edit_budget_save_button))
                    }
                }
            )
        }
    ) { contentPadding ->
        Box(modifier = Modifier.padding(contentPadding)) {
            EditBudgetInputs(uiState, actions)
        }
    }
}

@Composable
private fun EditBudgetInputs(uiState: EditBudgetUiState, actions: EditBudgetActions) {
    Column(Modifier.padding(vertical = 24.dp, horizontal = 16.dp), verticalArrangement = Arrangement.spacedBy(16.dp)) {
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = uiState.currentNameInput,
            onValueChange = actions::onNameInputChanged,
            label = { Text(text = stringResource(id = R.string.edit_budget_name_hint)) },
            singleLine = true,
        )
        Row(Modifier.fillMaxWidth()) {
            OutlinedTextField(
                modifier = Modifier.weight(1f),
                value = uiState.currentLimitInput,
                onValueChange = actions::onLimitInputChanged,
                label = { Text(text = stringResource(id = R.string.edit_budget_limit_hint)) },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                singleLine = true,
            )
            Spacer(modifier = Modifier.width(16.dp))
            val changeIntervalInteractionSource = remember { MutableInteractionSource() }
            val isPressed by changeIntervalInteractionSource.collectIsPressedAsState()
            LaunchedEffect(key1 = isPressed) {
                if (isPressed) {
                    actions.onChangeIntervalClicked()
                }
            }
            OutlinedTextField(
                modifier = Modifier.weight(1f),
                value = stringResource(uiState.currentInterval.name),
                onValueChange = {},
                interactionSource = changeIntervalInteractionSource,
                label = { Text(text = stringResource(id = R.string.edit_budget_limit_hint)) },
                trailingIcon = { Icon(Icons.Rounded.KeyboardArrowDown, contentDescription = null) },
                readOnly = true,
                singleLine = true,
            )
        }
        PercentageSlider(
            modifier = Modifier.fillMaxWidth(),
            value = uiState.currentExcessCarryOver,
            onValueChanged = actions::onExcessCarryOverInputChanged,
            title = stringResource(id = R.string.edit_budget_excess_carryover_percentage_title),
        )
        PercentageSlider(
            modifier = Modifier.fillMaxWidth(),
            value = uiState.currentOverdraftCarryOver,
            onValueChanged = actions::onOverdraftCarryOverInputChanged,
            title = stringResource(id = R.string.edit_budget_overdraft_carryover_percentage_title),
        )
    }
}

@Composable
private fun PercentageSlider(
    modifier: Modifier,
    value: Percentage,
    onValueChanged: (Percentage) -> Unit,
    title: String
) {
    Column(modifier) {
        Text(text = title, style = MaterialTheme.typography.titleMedium)
        Spacer(modifier = Modifier.height(8.dp))
        Row(Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
            Slider(
                modifier = Modifier.weight(1f),
                value = value.value,
                onValueChange = { onValueChanged(Percentage(it)) },
                steps = 19,
            )
            Spacer(modifier = Modifier.width(16.dp))
            Text(
                modifier = Modifier.width(40.dp),
                text = stringResource(id = R.string.percentage_value_format_string, value.intValue),
                style = MaterialTheme.typography.labelMedium,
                textAlign = TextAlign.End,
            )
        }
    }
}

@Preview
@Composable
fun EditBudgetPreview() {
    ComposedBudgetsTheme {
        EditBudgetContent(
            EditBudgetUiState("", "", Percentage(1f), Percentage(1f), BudgetInterval.Monthly),
            false,
            EditBudgetActionsFake()
        )
    }
}

class EditBudgetActionsFake : EditBudgetActions {
    override fun onNameInputChanged(input: String) {}
    override fun onLimitInputChanged(input: String) {}
    override fun onIntervalChanged(interval: BudgetInterval) {}
    override fun onChangeIntervalClicked() {}
    override fun onChangeIntervalSheetDismissed() {}
    override fun onExcessCarryOverInputChanged(percentage: Percentage) {}
    override fun onOverdraftCarryOverInputChanged(percentage: Percentage) {}
    override fun onCloseClicked() {}
    override fun onSaveClicked() {}
}

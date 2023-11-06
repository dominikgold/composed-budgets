package com.dominikgold.composedbudgets.features.budgets

import androidx.compose.foundation.clickable
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
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.dominikgold.composedbudgets.R
import com.dominikgold.composedbudgets.common.Percentage
import com.dominikgold.composedbudgets.domain.entities.BudgetInterval
import com.dominikgold.composedbudgets.domain.entities.name
import com.dominikgold.composedbudgets.ui.theme.ComposedBudgetsTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditBudgetUi() {
    val viewModel = viewModel<EditBudgetViewModel>()

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val changeIntervalBottomSheet by viewModel.changeIntervalSheet.collectAsStateWithLifecycle()
    val bottomSheetState = rememberModalBottomSheetState()

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
                    TextButton(onClick = actions::onSaveClicked) {
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
            OutlinedTextField(
                modifier = Modifier
                    .weight(1f)
                    .clickable(onClick = actions::onChangeIntervalClicked),
                value = stringResource(uiState.currentInterval.name),
                onValueChange = {},
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
private fun PercentageSlider(modifier: Modifier, value: Percentage, onValueChanged: (Percentage) -> Unit, title: String) {
    Column(modifier) {
        Text(text = title, style = MaterialTheme.typography.titleMedium)
        Spacer(modifier = Modifier.height(8.dp))
        Row(Modifier.fillMaxWidth()) {
            Slider(modifier = Modifier.weight(1f), value = value.value, onValueChange = { onValueChanged(Percentage(it)) }, steps = 21)
            Spacer(modifier = Modifier.width(16.dp))
            Text(
                text = stringResource(id = R.string.percentage_value_format_string, value.intValue),
                style = MaterialTheme.typography.labelMedium,
            )
        }
    }
}

@Preview
@Composable
fun EditBudgetPreview() {
    ComposedBudgetsTheme {
        EditBudgetContent(EditBudgetUiState("", "", Percentage(1f), Percentage(1f), BudgetInterval.Monthly), false, EditBudgetActionsFake())
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

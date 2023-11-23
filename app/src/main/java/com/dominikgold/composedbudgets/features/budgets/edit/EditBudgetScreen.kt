package com.dominikgold.composedbudgets.features.budgets.edit

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.KeyboardArrowDown
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.dominikgold.composedbudgets.R
import com.dominikgold.composedbudgets.domain.entities.BudgetId
import com.dominikgold.composedbudgets.domain.entities.BudgetInterval
import com.dominikgold.composedbudgets.domain.entities.name
import com.dominikgold.composedbudgets.ui.components.CloseButton
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
                            stringResource(id = R.string.edit_budget_title)
                        } else {
                            stringResource(id = R.string.add_budget_title)
                        },
                    )
                },
                navigationIcon = { CloseButton(actions::onCloseClicked) },
                actions = {
                    TextButton(onClick = actions::onSaveClicked, enabled = uiState.isSaveButtonEnabled) {
                        Text(text = stringResource(id = R.string.edit_budget_save_button))
                    }
                }
            )
        }
    ) { contentPadding ->
        Box(modifier = Modifier.padding(contentPadding)) {
            EditBudgetInputs(uiState, actions, isEditMode)
        }
    }
}

@Composable
private fun EditBudgetInputs(uiState: EditBudgetUiState, actions: EditBudgetActions, isEditMode: Boolean) {
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
        if (isEditMode) {
            Button(onClick = actions::onDeleteBudgetClicked, modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(16.dp)) {
                Text(text = stringResource(id = R.string.edit_budget_delete_button))
            }
        }
    }
}

@Preview
@Composable
fun EditBudgetPreview() {
    ComposedBudgetsTheme {
        EditBudgetContent(
            EditBudgetUiState("", "", BudgetInterval.Monthly),
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
    override fun onCloseClicked() {}
    override fun onSaveClicked() {}
    override fun onDeleteBudgetClicked() {}
}

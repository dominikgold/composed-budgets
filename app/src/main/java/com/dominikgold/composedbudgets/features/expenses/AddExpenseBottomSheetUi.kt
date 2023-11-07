package com.dominikgold.composedbudgets.features.expenses

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.dominikgold.composedbudgets.R
import com.dominikgold.composedbudgets.common.collectLifecycleAware
import com.dominikgold.composedbudgets.domain.entities.BudgetId
import com.dominikgold.composedbudgets.ui.theme.ComposedBudgetsTheme
import org.koin.androidx.compose.koinViewModel

data class AddExpenseBottomSheetData(val budgetId: BudgetId, val budgetName: String)

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun AddExpenseBottomSheetUi(data: AddExpenseBottomSheetData, dismissSheet: () -> Unit) {
    val viewModel = koinViewModel<AddExpenseViewModel>()
    val keyboardController = LocalSoftwareKeyboardController.current

    LaunchedEffect(data) {
        viewModel.setBudgetData(data.budgetId)
    }

    viewModel.dismissSheetEvents.collectLifecycleAware {
        keyboardController?.hide()
        dismissSheet()
    }

    val currentAmountInput by viewModel.currentAmountInput.collectAsStateWithLifecycle()
    val currentNameInput by viewModel.currentNameInput.collectAsStateWithLifecycle()

    AddExpenseBottomSheetContent(
        onDoneClicked = viewModel::onDoneClicked,
        budgetName = data.budgetName,
        currentAmountInput = currentAmountInput,
        onAmountInputChanged = viewModel::onAmountInputChanged,
        currentNameInput = currentNameInput,
        onNameInputChanged = viewModel::onNameInputChanged,
    )
}

@Composable
fun AddExpenseBottomSheetContent(
    onDoneClicked: () -> Unit,
    budgetName: String,
    currentAmountInput: String,
    onAmountInputChanged: (String) -> Unit,
    currentNameInput: String,
    onNameInputChanged: (String) -> Unit,
) {
    Column(
        Modifier
            .padding(vertical = 8.dp, horizontal = 16.dp)
            .navigationBarsPadding()
    ) {
        Row(Modifier.fillMaxWidth()) {
            Text(
                modifier = Modifier.weight(1f),
                text = stringResource(id = R.string.add_expense_sheet_title, budgetName),
                style = MaterialTheme.typography.headlineMedium,
            )
            Spacer(modifier = Modifier.width(8.dp))
            TextButton(onClick = onDoneClicked) {
                Text(text = stringResource(id = R.string.add_expense_sheet_done_button))
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
        Row(Modifier.fillMaxWidth()) {
            OutlinedTextField(
                modifier = Modifier.weight(.3f),
                value = currentAmountInput,
                onValueChange = onAmountInputChanged,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal, imeAction = ImeAction.Done),
                keyboardActions = KeyboardActions { onDoneClicked() },
                singleLine = true,
                label = { Text(stringResource(R.string.add_expense_amount_input_hint)) },
            )
            Spacer(modifier = Modifier.width(16.dp))
            OutlinedTextField(
                modifier = Modifier.weight(.7f),
                value = currentNameInput,
                onValueChange = onNameInputChanged,
                label = { Text(stringResource(R.string.add_expense_name_input_hint)) },
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                keyboardActions = KeyboardActions { onDoneClicked() },
                singleLine = true,
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
    }
}

@Preview
@Composable
fun AddExpenseBottomSheetPreview() {
    ComposedBudgetsTheme {
        AddExpenseBottomSheetContent({}, "Budget", "50", {}, "Expense", {})
    }
}

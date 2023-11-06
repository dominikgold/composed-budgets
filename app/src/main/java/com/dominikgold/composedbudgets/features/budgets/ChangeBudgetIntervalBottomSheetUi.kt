package com.dominikgold.composedbudgets.features.budgets

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Check
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.dominikgold.composedbudgets.R
import com.dominikgold.composedbudgets.domain.entities.BudgetInterval
import com.dominikgold.composedbudgets.domain.entities.name
import com.dominikgold.composedbudgets.ui.theme.ComposedBudgetsTheme

data class ChangeBudgetIntervalBottomSheetData(val selectedInterval: BudgetInterval)

@Composable
fun ChangeBudgetIntervalBottomSheetUi(data: ChangeBudgetIntervalBottomSheetData, onIntervalSelected: (BudgetInterval) -> Unit) {
    Column(Modifier.padding(vertical = 8.dp)) {
        val horizontalPadding = 16.dp
        Text(
            modifier = Modifier.padding(horizontal = horizontalPadding),
            text = stringResource(id = R.string.change_budget_interval_sheet_title),
            style = MaterialTheme.typography.headlineMedium,
        )
        BudgetIntervalButton(
            interval = BudgetInterval.Annually,
            isSelected = data.selectedInterval == BudgetInterval.Annually,
            horizontalPadding = horizontalPadding,
            onClicked = { onIntervalSelected(BudgetInterval.Annually) },
        )
        BudgetIntervalButton(
            interval = BudgetInterval.Monthly,
            isSelected = data.selectedInterval == BudgetInterval.Monthly,
            horizontalPadding = horizontalPadding,
            onClicked = { onIntervalSelected(BudgetInterval.Monthly) },
        )
        BudgetIntervalButton(
            interval = BudgetInterval.Daily,
            isSelected = data.selectedInterval == BudgetInterval.Daily,
            horizontalPadding = horizontalPadding,
            onClicked = { onIntervalSelected(BudgetInterval.Daily) },
        )
        BudgetIntervalButton(
            interval = BudgetInterval.OneTime,
            isSelected = data.selectedInterval == BudgetInterval.OneTime,
            horizontalPadding = horizontalPadding,
            onClicked = { onIntervalSelected(BudgetInterval.OneTime) },
        )
        Spacer(modifier = Modifier.height(16.dp))
    }
}

@Composable
private fun BudgetIntervalButton(interval: BudgetInterval, isSelected: Boolean, horizontalPadding: Dp, onClicked: () -> Unit) {
    TextButton(
        modifier = Modifier.fillMaxWidth(),
        onClick = onClicked,
        contentPadding = PaddingValues(horizontal = horizontalPadding, vertical = 8.dp),
    ) {
        Text(
            modifier = Modifier.weight(1f),
            text = stringResource(id = interval.name),
            color = if (isSelected) LocalContentColor.current else LocalContentColor.current.copy(alpha = .7f),
            style = MaterialTheme.typography.labelMedium
        )
        if (isSelected) {
            Icon(Icons.Rounded.Check, contentDescription = null)
        }
    }
}

@Preview
@Composable
fun ChangeBudgetIntervalBottomSheetPreview() {
    ComposedBudgetsTheme {
        ChangeBudgetIntervalBottomSheetUi(ChangeBudgetIntervalBottomSheetData(BudgetInterval.Monthly), {})
    }
}

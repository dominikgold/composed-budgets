package com.dominikgold.composedbudgets.features.overview

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.dominikgold.composedbudgets.R
import com.dominikgold.composedbudgets.domain.entities.BudgetId
import com.dominikgold.composedbudgets.features.expenses.AddExpenseBottomSheetUi
import com.dominikgold.composedbudgets.ui.theme.ComposedBudgetsTheme
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BudgetsOverviewUi() {
    val viewModel = viewModel<BudgetsOverviewViewModel>()

    val addExpenseBottomSheetData by viewModel.addExpenseBottomSheetData.collectAsStateWithLifecycle()
    val listItems by viewModel.listItems.collectAsStateWithLifecycle()
    val bottomSheetState = rememberModalBottomSheetState()
    val coroutineScope = rememberCoroutineScope()

    BudgetsOverviewContent(
        items = listItems,
        onBudgetClicked = viewModel::onBudgetClicked,
        onAddExpenseClicked = viewModel::onAddExpenseClicked,
    )

    addExpenseBottomSheetData?.let {
        ModalBottomSheet(
            onDismissRequest = viewModel::onAddExpenseBottomSheetDismissed,
            sheetState = bottomSheetState,
        ) {
            AddExpenseBottomSheetUi(
                data = it,
                dismissSheet = {
                    coroutineScope.launch {
                        bottomSheetState.hide()
                    }
                },
            )
        }
    }
}

@Composable
fun BudgetsOverviewContent(
    items: List<BudgetsOverviewListItem>,
    onBudgetClicked: (BudgetId) -> Unit,
    onAddExpenseClicked: (BudgetsOverviewListItem) -> Unit,
) {
    LazyColumn(
        modifier = Modifier.padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        contentPadding = PaddingValues(vertical = 24.dp),
    ) {
        item {
            Text(text = stringResource(id = R.string.budgets_overview_title), style = MaterialTheme.typography.headlineLarge)
        }
        items(items, key = { it.budgetId }) { listItem ->
            BudgetsOverviewListItemUi(
                item = listItem,
                onAddExpenseClicked = { onAddExpenseClicked(listItem) },
                onEditBudgetClicked = { onBudgetClicked(listItem.budgetId) },
            )
        }
    }
}

@Preview
@Composable
fun BudgetsOverviewPreview() {
    ComposedBudgetsTheme {
        BudgetsOverviewContent(listOf(), {}, {})
    }
}

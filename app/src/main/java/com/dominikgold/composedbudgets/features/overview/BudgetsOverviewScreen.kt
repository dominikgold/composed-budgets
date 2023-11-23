package com.dominikgold.composedbudgets.features.overview

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.dominikgold.composedbudgets.R
import com.dominikgold.composedbudgets.domain.entities.BudgetId
import com.dominikgold.composedbudgets.features.expenses.add.AddExpenseBottomSheetUi
import com.dominikgold.composedbudgets.features.expenses.add.AddExpenseHostViewModel
import com.dominikgold.composedbudgets.ui.theme.ComposedBudgetsTheme
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BudgetsOverviewUi() {
    val viewModel = koinViewModel<BudgetsOverviewViewModel>()
    val addExpenseHostViewModel = koinViewModel<AddExpenseHostViewModel>()

    val addExpenseBottomSheetData by addExpenseHostViewModel.addExpenseBottomSheetData.collectAsStateWithLifecycle()
    val listItems by viewModel.listItems.collectAsStateWithLifecycle()
    val bottomSheetState = rememberModalBottomSheetState()
    val density = LocalDensity.current
    var fabHeight by remember { mutableStateOf(0.dp) }

    Scaffold(
        floatingActionButton = {
            ExtendedFloatingActionButton(
                modifier = Modifier.onGloballyPositioned {
                    with(density) {
                        fabHeight = it.size.height.toDp()
                    }
                },
                onClick = viewModel::onAddBudgetClicked
            ) {
                Icon(Icons.Rounded.Add, modifier = Modifier.size(16.dp), contentDescription = null)
                Spacer(modifier = Modifier.width(8.dp))
                Text(text = stringResource(id = R.string.budgets_overview_add_budget_button))
            }
        },
    ) { contentPadding ->
        BudgetsOverviewContent(
            items = listItems,
            onBudgetClicked = viewModel::onBudgetClicked,
            onAddExpenseClicked = { addExpenseHostViewModel.onAddExpenseClicked(it.budgetId, it.name) },
            contentPadding = PaddingValues(
                top = contentPadding.calculateTopPadding(),
                bottom = contentPadding.calculateBottomPadding() + fabHeight
            ),
        )
    }

    addExpenseBottomSheetData?.let {
        ModalBottomSheet(
            onDismissRequest = addExpenseHostViewModel::onAddExpenseBottomSheetDismissed,
            sheetState = bottomSheetState,
        ) {
            AddExpenseBottomSheetUi(
                data = it,
                dismissSheet = addExpenseHostViewModel::onAddExpenseBottomSheetDismissed,
            )
        }
    }
}

@Composable
fun BudgetsOverviewContent(
    items: List<BudgetsOverviewListItem>,
    onBudgetClicked: (BudgetId) -> Unit,
    onAddExpenseClicked: (BudgetsOverviewListItem) -> Unit,
    contentPadding: PaddingValues,
) {
    LazyColumn(
        modifier = Modifier.padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        contentPadding = PaddingValues(
            top = contentPadding.calculateTopPadding() + 24.dp,
            bottom = contentPadding.calculateBottomPadding() + 24.dp,
        ),
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
        BudgetsOverviewContent(listOf(), {}, {}, PaddingValues())
    }
}

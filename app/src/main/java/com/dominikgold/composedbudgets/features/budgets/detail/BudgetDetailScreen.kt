package com.dominikgold.composedbudgets.features.budgets.detail

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.Edit
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MediumTopAppBar
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.dominikgold.composedbudgets.R
import com.dominikgold.composedbudgets.domain.entities.BudgetId
import com.dominikgold.composedbudgets.domain.entities.BudgetPeriod
import com.dominikgold.composedbudgets.domain.entities.Expense
import com.dominikgold.composedbudgets.domain.entities.ExpenseId
import com.dominikgold.composedbudgets.features.expenses.add.AddExpenseBottomSheetUi
import com.dominikgold.composedbudgets.features.expenses.add.AddExpenseHostActions
import com.dominikgold.composedbudgets.features.expenses.add.AddExpenseHostActionsFake
import com.dominikgold.composedbudgets.features.expenses.add.AddExpenseHostViewModel
import com.dominikgold.composedbudgets.ui.components.CloseButton
import com.dominikgold.composedbudgets.ui.theme.ComposedBudgetsTheme
import org.koin.androidx.compose.koinViewModel
import org.koin.core.parameter.parametersOf
import java.time.LocalDate
import java.time.Month
import java.time.ZonedDateTime

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BudgetDetailUi(budgetId: BudgetId) {
    val viewModel = koinViewModel<BudgetDetailViewModel>(parameters = { parametersOf(budgetId) })
    val addExpenseHostViewModel = koinViewModel<AddExpenseHostViewModel>()
    val snackbarHostState = remember { SnackbarHostState() }
    val context = LocalContext.current

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val budgetHistory by viewModel.budgetHistory.collectAsStateWithLifecycle()
    BudgetDetailContent(
        uiState = uiState,
        budgetHistory = budgetHistory,
        snackbarHostState = snackbarHostState,
        addExpenseHostActions = addExpenseHostViewModel,
        actions = viewModel,
    )

    val currentUndoDeleteSnackbar by viewModel.expenseMarkedForDeletion.collectAsStateWithLifecycle()
    LaunchedEffect(currentUndoDeleteSnackbar) {
        if (currentUndoDeleteSnackbar != null) {
            val result = snackbarHostState.showSnackbar(
                context.getString(R.string.expense_deleted_message),
                actionLabel = context.getString(R.string.undo_snackbar_button),
                duration = SnackbarDuration.Long,
            )
            when (result) {
                SnackbarResult.Dismissed -> viewModel.onUndoDeleteDismissed()
                SnackbarResult.ActionPerformed -> viewModel.onUndoDeleteClicked()
            }
        }
    }

    val bottomSheetState = rememberModalBottomSheetState()
    val addExpenseBottomSheetData by addExpenseHostViewModel.addExpenseBottomSheetData.collectAsStateWithLifecycle()
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BudgetDetailContent(
    uiState: BudgetDetailUiState,
    budgetHistory: BudgetHistoryUiModel?,
    snackbarHostState: SnackbarHostState,
    addExpenseHostActions: AddExpenseHostActions,
    actions: BudgetDetailActions
) {
    val density = LocalDensity.current
    var fabHeight by remember { mutableStateOf(0.dp) }
    Scaffold(
        topBar = {
            MediumTopAppBar(
                title = { Text(text = uiState.budgetName) },
                navigationIcon = { CloseButton(actions::onCloseClicked) },
                actions = {
                    IconButton(onClick = actions::onEditBudgetClicked) {
                        Icon(Icons.Rounded.Edit, contentDescription = "Edit")
                    }
                },
                scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior(),
            )
        },
        floatingActionButton = {
            ExtendedFloatingActionButton(
                modifier = Modifier.onGloballyPositioned {
                    with(density) {
                        fabHeight = it.size.height.toDp()
                    }
                },
                onClick = { addExpenseHostActions.onAddExpenseClicked(uiState.budgetId, uiState.budgetName) },
            ) {
                Icon(Icons.Rounded.Add, contentDescription = null)
                Spacer(modifier = Modifier.width(8.dp))
                Text(text = stringResource(id = R.string.budget_add_expense_button))
            }
        },
        snackbarHost = { SnackbarHost(snackbarHostState) },
    ) { contentPadding ->
        Box(modifier = Modifier.padding(contentPadding)) {
            ExpenseList(uiState, budgetHistory, fabHeight, actions::onDeleteExpenseClicked)
        }
    }
}

@Composable
private fun ExpenseList(
    uiState: BudgetDetailUiState,
    budgetHistory: BudgetHistoryUiModel?,
    extraBottomPadding: Dp,
    onDeleteExpenseClicked: (ExpenseId) -> Unit
) {
    LazyColumn(contentPadding = PaddingValues(top = 16.dp, bottom = 16.dp + extraBottomPadding)) {
        item {
            Column(Modifier.padding(horizontal = 16.dp)) {
                Text(
                    text = stringResource(id = R.string.budget_history_chart_subtitle),
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.labelLarge,
                )
                Spacer(modifier = Modifier.height(16.dp))
                budgetHistory?.let {
                    BudgetHistoryChartUi(
                        budgetHistory = it,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(240.dp),
                    )
                }
                Spacer(modifier = Modifier.height(24.dp))
            }
        }
        uiState.expenses.forEach { section ->
            item(key = section.header) {
                Text(
                    modifier = Modifier.padding(start = 16.dp, end = 16.dp, top = 16.dp, bottom = 8.dp),
                    text = section.header.format(LocalContext.current),
                    style = MaterialTheme.typography.titleMedium,
                    color = LocalContentColor.current.copy(alpha = .7f),
                )
            }
            itemsIndexed(section.items, key = { _, item -> item.expense.id }) { index, item ->
                ExpenseListItemUi(item = item, onDeleteClicked = { onDeleteExpenseClicked(item.expense.id) })
                if (index != section.items.lastIndex) {
                    HorizontalDivider()
                }
            }
        }
    }
}

@Preview
@Composable
fun BudgetDetailPreview() {
    ComposedBudgetsTheme {
        val expense = Expense(ExpenseId("expense"), "Aldi", ZonedDateTime.now(), BudgetId("budget"), 50.0)
        BudgetDetailContent(
            uiState = BudgetDetailUiState(
                budgetId = BudgetId("budget"),
                budgetName = "Groceries",
                budgetLimit = 100.0,
                expenses = listOf(
                    ExpenseListSection(
                        header = ExpenseListSectionHeader.Date(LocalDate.now()),
                        items = listOf(
                            ExpenseListItem(expense),
                            ExpenseListItem(expense.copy(id = ExpenseId("expense2"))),
                            ExpenseListItem(expense.copy(id = ExpenseId("expense3"))),
                        )
                    )
                )
            ),
            budgetHistory = BudgetHistoryUiModel(
                listOf(),
                budgetLimit = 500.0,
                currentBudgetPeriod = BudgetPeriod.Month(Month.NOVEMBER, 2023),
            ),
            snackbarHostState = SnackbarHostState(),
            addExpenseHostActions = AddExpenseHostActionsFake(),
            actions = BudgetDetailActionsFake(),
        )
    }
}

package com.dominikgold.composedbudgets.android.features.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MediumTopAppBar
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.dominikgold.composedbudgets.android.design.ComposedBudgetsTheme
import com.dominikgold.composedbudgets.android.utils.di.scopedKoinViewModel
import com.dominikgold.composedbudgets.entities.BudgetBreakdown
import com.dominikgold.composedbudgets.entities.Currency
import com.dominikgold.composedbudgets.entities.Expense
import com.dominikgold.composedbudgets.entities.MerchantCategory
import com.dominikgold.composedbudgets.entities.MonetaryValue
import com.dominikgold.composedbudgets.entities.MonthAndYear
import com.dominikgold.composedbudgets.features.home.ExpensesInMonthSummary
import com.dominikgold.composedbudgets.features.home.format
import kotlinx.datetime.Month

@Composable
fun HomeScreenDestination() {
    val viewModel = scopedKoinViewModel<HomeScreenViewModelContainer>().viewModel
    val runningExpensesBreakdownViewModel = viewModel.runningExpensesBreakdownViewModel

    val runningExpensesBreakdown by runningExpensesBreakdownViewModel.budgetBreakdown.collectAsStateWithLifecycle()
    val monthlySummaries by viewModel.monthlySummaries.collectAsStateWithLifecycle()

    HomeScreenContent(
        runningExpensesBreakdown = runningExpensesBreakdown,
        expensesInMonthSummaries = monthlySummaries,
        onSummaryClick = viewModel::onExpensesInMonthSummaryClick,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun HomeScreenContent(
    runningExpensesBreakdown: BudgetBreakdown,
    expensesInMonthSummaries: List<ExpensesInMonthSummary>,
    onSummaryClick: (ExpensesInMonthSummary) -> Unit,
) {
    Scaffold(
        topBar = {
            MediumTopAppBar(
                title = {
                    Text("Home", style = MaterialTheme.typography.headlineLarge)
                },
                scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(),
            )
        },
        floatingActionButton = {

        }
    ) { contentPadding ->
        LazyColumn(
            Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            contentPadding = PaddingValues(
                start = 16.dp,
                end = 16.dp,
                top = contentPadding.calculateTopPadding() + 16.dp,
                bottom = contentPadding.calculateBottomPadding() + 16.dp,
            ),
        ) {
            item {
                BudgetBreakdownUi(runningExpensesBreakdown, Modifier.fillMaxWidth())
            }
            items(expensesInMonthSummaries, key = { it.month }) { summary ->
                ExpensesInMonthSummaryCard(summary = summary, onClick = { onSummaryClick(summary) })
            }
        }
    }
}

@Composable
private fun ExpensesInMonthSummaryCard(summary: ExpensesInMonthSummary, onClick: () -> Unit) {
    Surface(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        shadowElevation = 2.dp,
        tonalElevation = 2.dp,
        onClick = onClick,
    ) {
        Column(Modifier.padding(16.dp)) {
            Text(summary.month.format(), style = MaterialTheme.typography.titleLarge)
            Spacer(Modifier.height(4.dp))
            Text(
                "Expenses: ${summary.totalExpensesAmount.toAmountString()}",
                style = MaterialTheme.typography.bodyMedium,
            )
        }
    }
}

@Suppress("MagicNumber")
@Preview
@Composable
private fun HomeScreenPreview() {
    ComposedBudgetsTheme {
        HomeScreenContent(
            BudgetBreakdown(
                listOf(MerchantCategory.preview),
                expenses = listOf(Expense.preview)
            ),
            listOf(
                ExpensesInMonthSummary(MonthAndYear(Month.MARCH, 2025), MonetaryValue(10000, Currency.Euro)),
                ExpensesInMonthSummary(MonthAndYear(Month.FEBRUARY, 2025), MonetaryValue(20000, Currency.Euro)),
            ),
            {},
        )
    }
}

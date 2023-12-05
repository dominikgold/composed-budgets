package com.dominikgold.composedbudgets.features.budgets.detail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.times
import com.dominikgold.composedbudgets.domain.entities.BudgetId
import com.dominikgold.composedbudgets.domain.entities.BudgetPeriod
import com.dominikgold.composedbudgets.domain.entities.Expense
import com.dominikgold.composedbudgets.domain.entities.ExpenseId
import com.dominikgold.composedbudgets.ui.components.VerticalBarChart
import com.dominikgold.composedbudgets.ui.theme.ComposedBudgetsTheme
import java.time.Month
import java.time.ZoneOffset
import java.time.ZonedDateTime

@Composable
fun BudgetHistoryChartUi(budgetHistory: BudgetHistoryUiModel, modifier: Modifier = Modifier) {
    BoxWithConstraints(modifier) {
        Row(
            Modifier
                .fillMaxSize()
                .padding(horizontal = 8.dp),
            verticalAlignment = Alignment.Bottom,
            horizontalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            budgetHistory.barChartData.forEach { barChartData ->
                VerticalBarChart(
                    barFillPercentage = barChartData.barFillPercentage,
                    modifier = Modifier
                        .fillMaxHeight()
                        .weight(1f),
                    barColor = if (barChartData.showErrorColor) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.primary,
                )
            }
        }
        val limitIndicatorOffset = maxHeight - (budgetHistory.budgetLimitIndicatorPlacement.value * maxHeight)
        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .height(1.dp)
                .background(color = MaterialTheme.colorScheme.onSurface)
                .offset(y = limitIndicatorOffset)
        )
        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .height(2.dp)
                .background(color = MaterialTheme.colorScheme.onSurface)
                .align(Alignment.BottomCenter)
        )
    }
}

@Preview
@Composable
fun BudgetHistoryChartUi() {
    ComposedBudgetsTheme {
        val november = ZonedDateTime.of(2023, 11, 1, 1, 1, 1, 1, ZoneOffset.UTC)
        val expense =
            Expense(ExpenseId("id1"), "name", november, BudgetId("budget"), 300.0)
        BudgetHistoryChartUi(
            budgetHistory = BudgetHistoryUiModel(
                listOf(
                    expense,
                    expense.copy(createdAt = november.minusMonths(1), amount = 600.0),
                    expense.copy(createdAt = november.minusMonths(2), amount = 100.0),
                    expense.copy(createdAt = november.minusMonths(4), amount = 300.0),
                    expense.copy(createdAt = november.minusMonths(5), amount = 10.0),
                ),
                budgetLimit = 500.0,
                currentBudgetPeriod = BudgetPeriod.Month(Month.NOVEMBER, 2023),
            ),
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
        )
    }
}

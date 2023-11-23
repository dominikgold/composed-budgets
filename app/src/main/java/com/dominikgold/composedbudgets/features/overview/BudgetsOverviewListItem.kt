package com.dominikgold.composedbudgets.features.overview

import android.content.Context
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.dominikgold.composedbudgets.R
import com.dominikgold.composedbudgets.domain.entities.Budget
import com.dominikgold.composedbudgets.domain.entities.BudgetId
import com.dominikgold.composedbudgets.domain.entities.BudgetInterval
import com.dominikgold.composedbudgets.domain.entities.BudgetPeriod
import com.dominikgold.composedbudgets.domain.entities.ExpensesInBudget
import com.dominikgold.composedbudgets.domain.entities.endTime
import com.dominikgold.composedbudgets.ui.theme.ComposedBudgetsTheme
import java.time.Period
import java.time.ZonedDateTime
import java.time.temporal.ChronoUnit

data class BudgetsOverviewListItem(
    private val expensesInBudget: ExpensesInBudget,
    private val currentTime: ZonedDateTime,
) {
    val budgetId = expensesInBudget.budget.id
    val name = expensesInBudget.budget.name
    val amountLeft = "${expensesInBudget.totalExpensedAmount.toInt()}/${expensesInBudget.budget.limit.toInt()}"
    val isOverdrafted = expensesInBudget.amountLeft < 0

    fun timeLeft(context: Context): String {
        if (expensesInBudget.budget.interval is BudgetInterval.OneTime) {
            return ""
        }

        val endOfPeriod = expensesInBudget.period.endTime
        val periodLeft = Period.between(currentTime.toLocalDate(), endOfPeriod.toLocalDate())
        val monthsLeft = periodLeft.months
        if (monthsLeft > 0) {
            return context.resources.getQuantityString(R.plurals.months_left_on_period_format, monthsLeft, monthsLeft)
        }

        val daysLeft = periodLeft.days
        if (daysLeft > 0) {
            return context.resources.getQuantityString(R.plurals.days_left_on_period_format, daysLeft, daysLeft)
        }

        val hoursLeft = ChronoUnit.HOURS.between(currentTime, endOfPeriod).toInt()
        if (hoursLeft > 0) {
            return context.resources.getQuantityString(R.plurals.hours_left_on_period_format, hoursLeft, hoursLeft)
        }
        return context.getString(R.string.budget_period_expires_very_soon)
    }
}

@Composable
fun BudgetsOverviewListItemUi(item: BudgetsOverviewListItem, onAddExpenseClicked: () -> Unit, onEditBudgetClicked: () -> Unit) {
    val context = LocalContext.current
    Surface(shadowElevation = 4.dp, tonalElevation = 4.dp, shape = RoundedCornerShape(16.dp), onClick = onEditBudgetClicked) {
        Column(Modifier.padding(16.dp)) {
            Row(Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
                Text(
                    modifier = Modifier.weight(1f),
                    text = item.name,
                    style = MaterialTheme.typography.headlineMedium,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(modifier = Modifier.width(16.dp))
                Text(
                    text = item.timeLeft(context),
                    style = MaterialTheme.typography.labelMedium,
                )
                Spacer(modifier = Modifier.width(12.dp))
            }
            Spacer(modifier = Modifier.height(8.dp))
            Row(Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = item.amountLeft,
                    color = if (item.isOverdrafted) MaterialTheme.colorScheme.error else Color.Unspecified,
                    style = MaterialTheme.typography.headlineSmall,
                )
                Spacer(modifier = Modifier.weight(1f))
                FilledTonalButton(onClick = onAddExpenseClicked, contentPadding = PaddingValues(horizontal = 12.dp)) {
                    Icon(Icons.Rounded.Add, modifier = Modifier.size(16.dp), contentDescription = null)
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(text = stringResource(id = R.string.budget_add_expense_button))
                }
            }
        }
    }
}

@Preview
@Composable
fun BudgetsOverviewListItemPreview() {
    ComposedBudgetsTheme {
        BudgetsOverviewListItemUi(
            BudgetsOverviewListItem(
                ExpensesInBudget(
                    Budget(BudgetId("1"), "Budget", BudgetInterval.Monthly, 600.0, ZonedDateTime.now()),
                    listOf(),
                    BudgetPeriod.Month(ZonedDateTime.now().month, 2023),
                ),
                ZonedDateTime.now(),
            ),
            {},
            {},
        )
    }
}

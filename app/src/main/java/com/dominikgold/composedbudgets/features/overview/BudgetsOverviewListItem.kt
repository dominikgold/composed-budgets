package com.dominikgold.composedbudgets.features.overview

import android.content.Context
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.dominikgold.composedbudgets.common.Percentage
import com.dominikgold.composedbudgets.domain.entities.Budget
import com.dominikgold.composedbudgets.domain.entities.BudgetId
import com.dominikgold.composedbudgets.domain.entities.BudgetInterval
import com.dominikgold.composedbudgets.domain.entities.BudgetPeriod
import com.dominikgold.composedbudgets.domain.entities.ExpensesInBudget
import com.dominikgold.composedbudgets.domain.entities.name
import com.dominikgold.composedbudgets.ui.theme.ComposedBudgetsTheme
import java.time.ZonedDateTime

data class BudgetsOverviewListItem(
    private val expensesInBudget: ExpensesInBudget,
    private val currentTime: ZonedDateTime,
) {
    val budgetId = expensesInBudget.budget.id
    val name = expensesInBudget.budget.name
    val amountLeft = "${expensesInBudget.totalExpensedAmount.toInt()}/${expensesInBudget.budget.limit.toInt()}"
    val isOverdrafted = expensesInBudget.amountLeft < 0

    fun budgetInterval(context: Context) = context.getString(expensesInBudget.budget.interval.name)

    fun timeLeft(context: Context): String {
        return "TODO days left"
    }
}

@Composable
fun BudgetsOverviewListItemUi(item: BudgetsOverviewListItem, onAddExpenseClicked: () -> Unit, onEditBudgetClicked: () -> Unit) {
    val context = LocalContext.current
    Surface(shadowElevation = 4.dp, shape = RoundedCornerShape(8.dp), onClick = onEditBudgetClicked) {
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
                    text = item.budgetInterval(context),
                    style = MaterialTheme.typography.labelMedium,
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
            Row(Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = item.amountLeft,
                    color = if (item.isOverdrafted) MaterialTheme.colorScheme.error else Color.Unspecified,
                    style = MaterialTheme.typography.headlineSmall,
                )
                Spacer(modifier = Modifier.width(16.dp))
                Text(
                    text = item.timeLeft(context),
                    style = MaterialTheme.typography.labelSmall,
                )
                Spacer(modifier = Modifier.weight(1f))
                IconButton(onClick = onAddExpenseClicked) {
                    Icon(Icons.Rounded.Add, contentDescription = null)
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
                    Budget(BudgetId("1"), "Budget", BudgetInterval.Monthly, 600.0, Percentage(1f), Percentage(1f), ZonedDateTime.now()),
                    listOf(),
                    0.0,
                    BudgetPeriod.Month(ZonedDateTime.now().month, 2023),
                ),
                ZonedDateTime.now(),
            ),
            {},
            {},
        )
    }
}

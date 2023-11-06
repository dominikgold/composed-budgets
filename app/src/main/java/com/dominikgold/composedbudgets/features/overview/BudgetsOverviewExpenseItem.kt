package com.dominikgold.composedbudgets.features.overview

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Delete
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.dominikgold.composedbudgets.common.formatShortDate
import com.dominikgold.composedbudgets.ui.theme.ComposedBudgetsTheme
import java.time.ZonedDateTime

data class BudgetsOverviewExpenseItem(
    val name: String,
    val amount: Int,
    val createdAt: ZonedDateTime,
)

@Composable
fun BudgetsOverviewExpenseItemUi(item: BudgetsOverviewExpenseItem, onDeleteClicked: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp, horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Column(Modifier.weight(1f)) {
            val title = item.name.ifEmpty { item.createdAt.formatShortDate() }
            val subtitle = if (item.name.isNotEmpty()) item.createdAt.formatShortDate() else ""
            Text(text = title, style = MaterialTheme.typography.labelMedium, maxLines = 1, overflow = TextOverflow.Ellipsis)
            Spacer(modifier = Modifier.height(2.dp))
            Text(text = subtitle, style = MaterialTheme.typography.labelSmall)
        }
        Spacer(modifier = Modifier.width(16.dp))
        Text(text = item.amount.toString(), style = MaterialTheme.typography.labelMedium)
        IconButton(onClick = onDeleteClicked) {
            Icon(Icons.Rounded.Delete, contentDescription = "Delete")
        }
    }
}

@Preview
@Composable
fun BudgetsOverviewExpenseItemPreview() {
    ComposedBudgetsTheme {
        BudgetsOverviewExpenseItemUi(BudgetsOverviewExpenseItem("Expense", 50, ZonedDateTime.now()), {})
    }
}

package com.dominikgold.composedbudgets.features.budgets.detail

import android.content.Context
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.dominikgold.composedbudgets.common.formatShortDate
import com.dominikgold.composedbudgets.domain.entities.Expense
import java.time.LocalDate
import java.time.format.TextStyle
import java.util.Locale

sealed interface ExpenseListSectionHeader {

    fun format(context: Context): String

    data class Date(private val date: LocalDate) : ExpenseListSectionHeader {
        override fun format(context: Context): String {
            return date.formatShortDate()
        }
    }

    data class Month(private val month: java.time.Month, private val year: Int, private val currentYear: Int) : ExpenseListSectionHeader {
        override fun format(context: Context): String {
            if (year < currentYear) {
                return "${month.getDisplayName(TextStyle.SHORT, context.resources.configuration.locales[0])} $year"
            }
            return month.getDisplayName(TextStyle.FULL_STANDALONE, Locale.getDefault())
        }
    }
}

data class ExpenseListSection(
    val header: ExpenseListSectionHeader,
    val items: List<ExpenseListItem>,
)

data class ExpenseListItem(val expense: Expense) {

    val amount: String = expense.amount.toString()
    val name = expense.name
}

@Composable
fun ExpenseListItemUi(item: ExpenseListItem, onDeleteClicked: () -> Unit) {
    Row {
        Text(text = item.name, style = MaterialTheme.typography.labelMedium)
        Spacer(modifier = Modifier.weight(1f))
        Text(text = item.amount, style = MaterialTheme.typography.labelLarge)
    }
}

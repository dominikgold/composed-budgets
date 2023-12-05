package com.dominikgold.composedbudgets.features.budgets.detail

import android.content.Context
import android.os.Parcelable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.AnchoredDraggableState
import androidx.compose.foundation.gestures.DraggableAnchors
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.anchoredDraggable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import com.dominikgold.composedbudgets.R
import com.dominikgold.composedbudgets.common.formatShortDate
import com.dominikgold.composedbudgets.domain.entities.Expense
import kotlinx.parcelize.Parcelize
import java.time.LocalDate
import java.time.format.TextStyle
import java.util.Locale
import kotlin.math.roundToInt

sealed interface ExpenseListSectionHeader : Parcelable {

    fun format(context: Context): String

    @Parcelize
    data class Date(private val date: LocalDate) : ExpenseListSectionHeader {
        override fun format(context: Context): String {
            return date.formatShortDate()
        }
    }

    @Parcelize
    data class Month(private val month: java.time.Month, private val year: Int, private val currentYear: Int) : ExpenseListSectionHeader {
        override fun format(context: Context): String {
            if (year < currentYear) {
                return "${month.getDisplayName(TextStyle.SHORT, context.resources.configuration.locales[0])} $year"
            }
            return month.getDisplayName(TextStyle.FULL_STANDALONE, Locale.getDefault())
        }
    }

    @Parcelize
    data object All : ExpenseListSectionHeader {
        override fun format(context: Context): String {
            return context.getString(R.string.budget_all_expenses_header)
        }
    }
}

data class ExpenseListSection(
    val header: ExpenseListSectionHeader,
    val items: List<ExpenseListItem>,
)

data class ExpenseListItem(val expense: Expense) {

    val amount: String = expense.formattedAmount

    fun name(context: Context) = expense.name.ifEmpty {
        context.getString(R.string.unnamed_expense_fallback)
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ExpenseListItemUi(item: ExpenseListItem, onDeleteClicked: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(IntrinsicSize.Min),
    ) {
        val density = LocalDensity.current
        val actionSize = 64.dp
        val actionSizePx = with(density) { actionSize.toPx() }
        val draggableState = remember {
            AnchoredDraggableState(
                initialValue = false,
                anchors = DraggableAnchors {
                    false at 0f
                    true at actionSizePx
                },
                positionalThreshold = { distance: Float -> distance * 0.5f },
                velocityThreshold = { with(density) { 100.dp.toPx() } },
                animationSpec = tween(),
            )
        }

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.errorContainer),
            contentAlignment = Alignment.CenterStart,
        ) {
            IconButton(modifier = Modifier.width(actionSize), onClick = onDeleteClicked) {
                Icon(Icons.Outlined.Delete, modifier = Modifier.size(24.dp), contentDescription = "Delete")
            }
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .offset {
                    IntOffset(
                        x = draggableState
                            .requireOffset()
                            .roundToInt(),
                        y = 0,
                    )
                }
                .anchoredDraggable(draggableState, Orientation.Horizontal, reverseDirection = false)
                .background(MaterialTheme.colorScheme.background)
        ) {
            Row(
                Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = item.name(LocalContext.current), style = MaterialTheme.typography.labelLarge)
                Spacer(modifier = Modifier.weight(1f))
                Text(text = item.amount, style = MaterialTheme.typography.titleMedium)
            }
        }
    }
}

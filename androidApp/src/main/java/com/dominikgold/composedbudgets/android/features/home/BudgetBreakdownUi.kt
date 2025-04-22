package com.dominikgold.composedbudgets.android.features.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.dominikgold.composedbudgets.android.design.ThemeColor
import com.dominikgold.composedbudgets.entities.BudgetBreakdown

@Composable
fun BudgetBreakdownUi(breakdown: BudgetBreakdown, modifier: Modifier = Modifier) {
    Surface(
        modifier = modifier,
        shape = RoundedCornerShape(20.dp),
        shadowElevation = 4.dp,
        tonalElevation = 4.dp,
    ) {
        Column(Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
            BudgetBreakdownItem("Total", breakdown.totalAmount.toAmountString())
            breakdown.breakdownByCategory.forEach {
                BudgetBreakdownItem(it.category.name, it.totalAmount.toAmountString())
            }
        }
    }
}

@Composable
fun BudgetBreakdownItem(title: String, amount: String) {
    Row(Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
        Text(title, style = MaterialTheme.typography.titleMedium, modifier = Modifier.weight(1f))
        Spacer(Modifier.width(16.dp))
        Text(amount, style = MaterialTheme.typography.displayMedium)
    }
}

// TODO implement Pie Chart at some point
//data class PieChartDataPoint(val name: String, val value: Double, val color: ThemeColor)
//
//data class PieChartState(val data: List<PieChartDataPoint>) {
//
//    val totalAmount = data.sumOf { it.value }
//}
//
//@Composable
//fun PieChart(data: List<PieChartDataPoint>, modifier: Modifier = Modifier) {
//
//}

package com.dominikgold.composedbudgets.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.dominikgold.composedbudgets.common.Percentage

@Composable
fun HorizontalBarChart(
    barFillPercentage: Percentage,
    modifier: Modifier = Modifier,
    barColor: Color = MaterialTheme.colorScheme.primary,
    secondBarFillPercentage: Percentage = Percentage(0f),
    secondBarColor: Color = MaterialTheme.colorScheme.secondary,
) {
    BarChart(
        barFillPercentage = barFillPercentage,
        vertical = false,
        modifier = modifier,
        barColor = barColor,
        secondBarFillPercentage = secondBarFillPercentage,
        secondBarColor = secondBarColor,
    )
}

@Composable
fun VerticalBarChart(
    barFillPercentage: Percentage,
    modifier: Modifier = Modifier,
    barColor: Color = MaterialTheme.colorScheme.primary,
    secondBarFillPercentage: Percentage = Percentage(0f),
    secondBarColor: Color = MaterialTheme.colorScheme.secondary,
) {
    BarChart(
        barFillPercentage = barFillPercentage,
        vertical = true,
        modifier = modifier,
        barColor = barColor,
        secondBarFillPercentage = secondBarFillPercentage,
        secondBarColor = secondBarColor,
    )
}

@Composable
private fun BarChart(
    barFillPercentage: Percentage,
    vertical: Boolean,
    modifier: Modifier = Modifier,
    barColor: Color = MaterialTheme.colorScheme.primary,
    secondBarFillPercentage: Percentage = Percentage(0f),
    secondBarColor: Color = MaterialTheme.colorScheme.secondary,
) {
    Box(modifier = modifier, contentAlignment = Alignment.BottomStart) {
        val barShape = if (vertical) {
            RoundedCornerShape(topEnd = 8.dp, topStart = 8.dp)
        } else {
            RoundedCornerShape(topEnd = 8.dp, bottomEnd = 8.dp)
        }
        Box(
            modifier = Modifier
                .fillMaxHeight(if (vertical) barFillPercentage.value else 1f)
                .fillMaxWidth(if (!vertical) barFillPercentage.value else 1f)
                .clip(barShape)
                .background(barColor.copy(alpha = .3f))
                .border(1.dp, barColor, barShape),
        )
        Box(
            modifier = Modifier
                .fillMaxHeight(if (vertical) secondBarFillPercentage.value else 1f)
                .fillMaxWidth(if (!vertical) secondBarFillPercentage.value else 1f)
                .clip(barShape)
                .background(secondBarColor.copy(alpha = .3f))
                .border(1.dp, secondBarColor, barShape),
        )
    }
}

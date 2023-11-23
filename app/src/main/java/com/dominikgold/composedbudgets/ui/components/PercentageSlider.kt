package com.dominikgold.composedbudgets.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.dominikgold.composedbudgets.R
import com.dominikgold.composedbudgets.common.Percentage

@Composable
fun PercentageSlider(
    modifier: Modifier,
    value: Percentage,
    onValueChanged: (Percentage) -> Unit,
    title: String
) {
    Column(modifier) {
        Text(text = title, style = MaterialTheme.typography.titleMedium)
        Spacer(modifier = Modifier.height(8.dp))
        Row(Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
            Slider(
                modifier = Modifier.weight(1f),
                value = value.value,
                onValueChange = { onValueChanged(Percentage(it)) },
                steps = 19,
            )
            Spacer(modifier = Modifier.width(16.dp))
            Text(
                modifier = Modifier.width(40.dp),
                text = stringResource(id = R.string.percentage_value_format_string, value.intValue),
                style = MaterialTheme.typography.labelMedium,
                textAlign = TextAlign.End,
            )
        }
    }
}

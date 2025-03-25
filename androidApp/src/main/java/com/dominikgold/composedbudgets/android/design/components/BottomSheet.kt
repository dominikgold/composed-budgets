package com.dominikgold.composedbudgets.android.design.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.dominikgold.composedbudgets.android.design.Colors
import com.dominikgold.composedbudgets.android.design.TextStyles

@Composable
fun StandardBottomSheetContainer(content: @Composable ColumnScope.() -> Unit) {
    Surface(
        shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp),
        modifier = Modifier
            .widthIn(max = TabletBoxedUiWidth)
            .fillMaxWidth(),
    ) {
        Column(
            Modifier
                .padding(horizontal = 24.dp, vertical = 16.dp)
                .navigationBarsPadding()
                .imePadding(),
        ) {
            content()
        }
    }
}

@Composable
fun ColumnScope.StandardBottomSheetHeader(text: String) {
    BottomSheetDragHandle()
    Spacer(modifier = Modifier.height(12.dp))
    Text(
        text = text,
        style = TextStyles.HeadlineMedium
    )
}

@Composable
fun ColumnScope.BottomSheetDragHandle() {
    Box(
        modifier = Modifier
            .width(32.dp)
            .height(4.dp)
            .align(CenterHorizontally)
            .clip(RoundedCornerShape(2.dp))
            .background(color = Colors.PrimaryVariant.dynamic())
    )
}

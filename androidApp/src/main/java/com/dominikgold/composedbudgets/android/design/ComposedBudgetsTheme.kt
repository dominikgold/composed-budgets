package com.dominikgold.composedbudgets.android.design

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.compositionLocalOf

@Composable
fun ComposedBudgetsTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit,
) {
    CompositionLocalProvider(value = LocalUseDarkTheme provides darkTheme) {
        MaterialTheme(
            colorScheme = if (darkTheme) darkColors else lightColors,
            typography = Typography,
        ) {
            Surface(content = content)
        }
    }
}

val LocalUseDarkTheme = compositionLocalOf { false }

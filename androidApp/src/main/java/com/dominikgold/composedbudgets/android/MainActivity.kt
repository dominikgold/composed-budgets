package com.dominikgold.composedbudgets.android

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.toArgb
import com.dominikgold.composedbudgets.android.design.Colors
import com.dominikgold.composedbudgets.android.design.ComposedBudgetsTheme
import com.dominikgold.composedbudgets.android.navigation.MainNavigation
import com.dominikgold.composedbudgets.navigation.Navigator
import org.koin.android.ext.android.inject

class MainActivity : ComponentActivity() {

    private val navigator: Navigator by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LaunchedEffect(Unit) {
                this@MainActivity.enableEdgeToEdge(
                    statusBarStyle = SystemBarStyle.auto(
                        Colors.SystemBarsOverlay.toArgb(),
                        Colors.SystemBarsOverlay.toArgb()
                    ),
                    navigationBarStyle = SystemBarStyle.auto(
                        Colors.SystemBarsOverlay.toArgb(),
                        Colors.SystemBarsOverlay.toArgb()
                    ),
                )
            }
            ComposedBudgetsTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MainNavigation(navigator)
                }
            }
        }
    }
}

package com.dominikgold.composedbudgets

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import com.dominikgold.composedbudgets.navigation.LocalNavigator
import com.dominikgold.composedbudgets.navigation.MainNavigation
import com.dominikgold.composedbudgets.navigation.NavigationEvents
import com.dominikgold.composedbudgets.ui.theme.ComposedBudgetsTheme
import org.koin.android.ext.android.get

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposedBudgetsTheme {
                val navigationEvents = get<NavigationEvents>()
                CompositionLocalProvider(LocalNavigator provides get()) {
                    // A surface container using the 'background' color from the theme
                    Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
                        MainNavigation(navigationEvents)
                    }
                }
            }
        }
    }
}

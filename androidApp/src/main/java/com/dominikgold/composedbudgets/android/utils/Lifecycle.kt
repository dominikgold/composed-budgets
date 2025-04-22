package com.dominikgold.composedbudgets.android.utils

import android.annotation.SuppressLint
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.flowWithLifecycle
import com.dominikgold.composedbudgets.android.navigation.LocalBackStackEntry
import com.dominikgold.composedbudgets.utils.coroutines.UiEventsFlow

@SuppressLint("ComposableNaming")
@Composable
fun <T> UiEventsFlow<T>.collectLifecycleAware(onEach: suspend (T) -> Unit) {
    val backStackEntry = LocalBackStackEntry.current
    val lifecycleFlow = remember(this, backStackEntry) {
        this.flowWithLifecycle(backStackEntry.lifecycle, Lifecycle.State.RESUMED)
    }
    LaunchedEffect(key1 = lifecycleFlow) {
        lifecycleFlow.collect { onEach(it) }
    }
}

@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun <T> UiEventsFlow<T>.collectActivityLifecycleAware(onEach: suspend (T) -> Unit) {
    val lifecycleOwner = LocalLifecycleOwner.current
    val lifecycleFlow = remember(this, lifecycleOwner) {
        this.flowWithLifecycle(lifecycleOwner.lifecycle, Lifecycle.State.STARTED)
    }
    LaunchedEffect(key1 = lifecycleFlow) {
        lifecycleFlow.collect { onEach(it) }
    }
}

package com.dominikgold.composedbudgets.common

import android.annotation.SuppressLint
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

fun <T> Flow<T>.throttleFirst(windowDuration: Long): Flow<T> = flow {
    var lastEmissionTime = 0L
    collect { value ->
        val currentTime = System.currentTimeMillis()
        val delta = currentTime - lastEmissionTime
        if (delta > windowDuration) {
            lastEmissionTime = currentTime
            emit(value)
        }
    }
}

@SuppressLint("ComposableNaming")
@Composable
fun <T> Flow<T>.collectLifecycleAware(onEach: suspend (T) -> Unit) {
    val lifecycleOwner = LocalLifecycleOwner.current
    val lifecycleFlow = remember(this, lifecycleOwner) {
        this.flowWithLifecycle(lifecycleOwner.lifecycle, Lifecycle.State.STARTED)
    }
    LaunchedEffect(key1 = lifecycleFlow) {
        lifecycleFlow.collect { onEach(it) }
    }
}

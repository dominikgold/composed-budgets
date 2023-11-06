package com.dominikgold.composedbudgets.common

import android.annotation.SuppressLint
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.flow.receiveAsFlow

class UiEventsFlow<T> : Flow<T> {

    private val channel = Channel<T>(Channel.BUFFERED)

    suspend fun emit(item: T) {
        channel.send(item)
    }

    fun tryEmit(item: T): Boolean {
        return channel.trySend(item).isSuccess
    }

    override suspend fun collect(collector: FlowCollector<T>) {
        channel.receiveAsFlow()
            .collect(collector)
    }
}

@SuppressLint("ComposableNaming")
@Composable
fun <T> UiEventsFlow<T>.collectLifecycleAware(onEach: suspend (T) -> Unit) {
    val lifecycleOwner = LocalLifecycleOwner.current
    val lifecycleFlow = remember(this, lifecycleOwner) {
        this.flowWithLifecycle(lifecycleOwner.lifecycle, Lifecycle.State.STARTED)
    }
    LaunchedEffect(key1 = lifecycleFlow) {
        lifecycleFlow.collect { onEach(it) }
    }
}

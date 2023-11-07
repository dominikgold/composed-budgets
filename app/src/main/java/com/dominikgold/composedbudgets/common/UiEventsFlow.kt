package com.dominikgold.composedbudgets.common

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

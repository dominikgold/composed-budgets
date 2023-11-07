package com.dominikgold.composedbudgets.utils

import kotlinx.coroutines.TimeoutCancellationException
import kotlinx.coroutines.cancelAndJoin
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.withTimeout
import kotlin.time.Duration.Companion.milliseconds
import kotlin.time.Duration.Companion.seconds

/**
 * Collect the receiving [Flow] in the given [TestScope], then run assertions on the flow's emissions in the given
 * [assertionBlock] by using the available functions in [TestFlowCollector]. Usually, you can simply pass the scope
 * given in the [runTest] function.
 */
suspend fun <T> Flow<T>.test(scope: TestScope, assertionBlock: suspend TestFlowCollector<T>.() -> Unit = {}) {
    with(TestFlowCollector(this, scope)) {
        assertionBlock()
        finishAssertion()
    }
}

class TestFlowCollector<T>(private val flow: Flow<T>, scope: TestScope) {

    private val values = mutableListOf<T>()

    private val job = scope.launch(UnconfinedTestDispatcher(scope.testScheduler)) {
        flow.collect {
            values.add(it)
        }
    }

    suspend fun finishAssertion() {
        job.cancelAndJoin()
    }

    @Suppress("SwallowedException")
    suspend fun awaitValue(at: Int): T {
        return try {
            withTimeout(1.seconds) {
                while (values.getOrNull(at) == null) {
                    delay(50.milliseconds)
                }
                values[at]
            }
        } catch (e: TimeoutCancellationException) {
            throw AssertionError("No value was emitted within 1 second at index $at")
        }
    }

    @Suppress("SwallowedException")
    suspend fun awaitLastValue(): T {
        return try {
            withTimeout(1.seconds) {
                while (!job.isCompleted) {
                    delay(50.milliseconds)
                }
                values.last()
            }
        } catch (e: TimeoutCancellationException) {
            throw AssertionError("Flow has not completed within 1 second")
        }
    }

    fun assertIsCompleted() {
        if (!job.isCompleted) {
            throw AssertionError("Flow is still running")
        }
    }

    @Suppress("SwallowedException", "InstanceOfCheckForException")
    suspend fun ensureNoValue(at: Int) {
        try {
            val value = awaitValue(at)
            throw UnexpectedValueException(value, at)
        } catch (e: AssertionError) {
            if (e is UnexpectedValueException) {
                throw e
            }
            // all good, expected the exception to be thrown
        }
    }

    private class UnexpectedValueException(value: Any?, at: Int) :
        AssertionError("Expected no value to be emitted at index $at but received: $value")
}
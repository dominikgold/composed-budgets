package com.dominikgold.composedbudgets.utils.di

import com.dominikgold.composedbudgets.utils.DateTimeProvider
import com.dominikgold.composedbudgets.utils.DefaultDateTimeProvider
import com.dominikgold.composedbudgets.utils.coroutines.CoroutineDispatcherProvider
import com.dominikgold.composedbudgets.utils.coroutines.DefaultCoroutineDispatcherProvider
import org.koin.dsl.bind
import org.koin.dsl.module

val utilsModule = module {
    factory { DefaultCoroutineDispatcherProvider() } bind CoroutineDispatcherProvider::class
    factory { DefaultDateTimeProvider() } bind DateTimeProvider::class
}

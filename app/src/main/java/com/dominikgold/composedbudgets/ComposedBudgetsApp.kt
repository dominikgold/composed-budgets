package com.dominikgold.composedbudgets

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.dsl.koinApplication

class ComposedBudgetsApp : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@ComposedBudgetsApp)
//            androidLogger()
            modules(appModule)
        }
    }
}

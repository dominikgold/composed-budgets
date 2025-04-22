package com.dominikgold.composedbudgets.android

import android.app.Application
import com.dominikgold.composedbudgets.android.di.appModule
import com.dominikgold.composedbudgets.utils.Logger
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class ComposedBudgetsApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@ComposedBudgetsApplication)
            androidLogger()
            modules(appModule)
        }

        Logger.init()
    }
}

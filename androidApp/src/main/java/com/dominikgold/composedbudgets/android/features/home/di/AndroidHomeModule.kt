package com.dominikgold.composedbudgets.android.features.home.di

import com.dominikgold.composedbudgets.android.features.home.HomeScreenViewModelContainer
import com.dominikgold.composedbudgets.features.home.homeModule
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val androidHomeModule = module {
    includes(homeModule)
    viewModel { HomeScreenViewModelContainer() }
}

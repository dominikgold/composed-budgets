package com.dominikgold.composedbudgets.android.features.home

import com.dominikgold.composedbudgets.android.utils.di.ViewModelContainer
import com.dominikgold.composedbudgets.android.utils.di.injectViewModel
import com.dominikgold.composedbudgets.features.home.HomeViewModel

class HomeScreenViewModelContainer : ViewModelContainer() {

    val viewModel by injectViewModel<HomeViewModel>()
}

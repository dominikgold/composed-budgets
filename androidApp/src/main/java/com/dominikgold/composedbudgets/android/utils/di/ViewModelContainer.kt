package com.dominikgold.composedbudgets.android.utils.di

import androidx.compose.runtime.Composable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dominikgold.composedbudgets.android.navigation.LocalBackStackEntry
import org.koin.androidx.compose.koinViewModel
import org.koin.androidx.scope.ScopeViewModel
import org.koin.core.parameter.ParametersDefinition
import org.koin.core.parameter.parametersOf

abstract class ViewModelContainer : ScopeViewModel()

inline fun <reified T : Any> ViewModelContainer.injectViewModel(vararg params: Any): Lazy<T> =
    scope.inject<T> { parametersOf(params, viewModelScope) }

@Composable
inline fun <reified T : ViewModel> scopedKoinViewModel(
    key: String? = null,
    noinline parameters: ParametersDefinition? = null,
): T {
    val backStackEntry = LocalBackStackEntry.current
    return koinViewModel<T>(key = key, parameters = parameters, viewModelStoreOwner = backStackEntry)
}

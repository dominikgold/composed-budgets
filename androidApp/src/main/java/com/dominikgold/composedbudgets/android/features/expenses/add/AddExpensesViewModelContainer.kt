package com.dominikgold.composedbudgets.android.features.expenses.add

import com.dominikgold.composedbudgets.android.utils.di.ViewModelContainer
import com.dominikgold.composedbudgets.android.utils.di.injectViewModel
import com.dominikgold.composedbudgets.features.expenses.add.AddExpensesViewModel

class AddExpensesViewModelContainer : ViewModelContainer() {

    val viewModel by injectViewModel<AddExpensesViewModel>()
}

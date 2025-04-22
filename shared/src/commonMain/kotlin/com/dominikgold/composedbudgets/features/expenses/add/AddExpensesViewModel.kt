package com.dominikgold.composedbudgets.features.expenses.add

import com.dominikgold.composedbudgets.navigation.Navigator
import com.dominikgold.composedbudgets.utils.files.LocalFileResource
import kotlinx.coroutines.flow.StateFlow

class AddExpensesViewModel(private val navigator: Navigator) {

    val uiState: StateFlow<AddExpensesUiState> = TODO()

    var hasPickedFile: Boolean = false

    fun onFileSelected(file: LocalFileResource) {

    }
}

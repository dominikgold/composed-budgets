package com.dominikgold.composedbudgets.features.budgets.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dominikgold.composedbudgets.common.DateTimeProvider
import com.dominikgold.composedbudgets.common.toLocalDateWithZone
import com.dominikgold.composedbudgets.domain.entities.Budget
import com.dominikgold.composedbudgets.domain.entities.BudgetId
import com.dominikgold.composedbudgets.domain.entities.Expense
import com.dominikgold.composedbudgets.domain.entities.ExpenseId
import com.dominikgold.composedbudgets.features.budgets.usecases.GetAllExpensesInBudget
import com.dominikgold.composedbudgets.features.budgets.usecases.GetBudget
import com.dominikgold.composedbudgets.features.expenses.usecases.DeleteExpense
import com.dominikgold.composedbudgets.navigation.Destination
import com.dominikgold.composedbudgets.navigation.Navigator
import kotlinx.coroutines.NonCancellable
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class BudgetDetailViewModel(
    private val budgetId: BudgetId,
    private val getBudget: GetBudget,
    private val getAllExpensesInBudget: GetAllExpensesInBudget,
    private val deleteExpense: DeleteExpense,
    private val navigator: Navigator,
    private val dateTimeProvider: DateTimeProvider,
) : ViewModel(), BudgetDetailActions {

    val expenseMarkedForDeletion = MutableStateFlow<ExpenseId?>(null)

    val uiState: StateFlow<BudgetDetailUiState> = combine(
        getBudget.observe(budgetId),
        getAllExpensesInBudget.observe(budgetId),
    ) { budget, expenses ->
        combineToUiState(budget, expenses)
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(), BudgetDetailUiState(budgetId, "", listOf()))

    override fun onEditBudgetClicked() {
        navigator.navigateTo(Destination.EditBudget(budgetId))
    }

    override fun onDeleteExpenseClicked(expenseId: ExpenseId) {
        expenseMarkedForDeletion.value = expenseId
    }

    override fun onUndoDeleteDismissed() {
        val expenseId = expenseMarkedForDeletion.value ?: return
        expenseMarkedForDeletion.value = null
        viewModelScope.launch {
            withContext(NonCancellable) {
                deleteExpense.delete(expenseId)
            }
        }
    }

    override fun onUndoDeleteClicked() {
        expenseMarkedForDeletion.value = null
    }

    override fun onCloseClicked() {
        navigator.goBack()
    }

    private fun combineToUiState(budget: Budget, expenses: List<Expense>): BudgetDetailUiState {
        return BudgetDetailUiState(
            budgetId = budget.id,
            budgetName = budget.name,
            expenses = expenses.divideIntoSections(),
        )
    }

    private val currentYear = dateTimeProvider.today().year

    private fun List<Expense>.divideIntoSections() = this.groupBy { expense ->
        // Group expenses by the month (and year) in which they were created
        expense.createdAt.toLocalDateWithZone().withDayOfMonth(1)
    }.map { (date, expenses) ->
        ExpenseListSection(
            header = ExpenseListSectionHeader.Month(date.month, date.year, currentYear),
            items = expenses.map { ExpenseListItem(it) },
        )
    }
}

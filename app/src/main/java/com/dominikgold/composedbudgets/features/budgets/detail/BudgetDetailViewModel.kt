package com.dominikgold.composedbudgets.features.budgets.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dominikgold.composedbudgets.common.DateTimeProvider
import com.dominikgold.composedbudgets.domain.entities.Budget
import com.dominikgold.composedbudgets.domain.entities.BudgetId
import com.dominikgold.composedbudgets.domain.entities.BudgetInterval
import com.dominikgold.composedbudgets.domain.entities.BudgetPeriod
import com.dominikgold.composedbudgets.domain.entities.Expense
import com.dominikgold.composedbudgets.domain.entities.ExpenseId
import com.dominikgold.composedbudgets.domain.entities.toCorrespondingBudgetPeriod
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

    private val expensesFlow = getAllExpensesInBudget.observe(budgetId)
        .combine(expenseMarkedForDeletion) { expenses, expenseMarkedForDeletion ->
            expenses.filter { it.id != expenseMarkedForDeletion }
        }
    private val budgetFlow = getBudget.observe(budgetId)

    val uiState: StateFlow<BudgetDetailUiState> = combine(
        budgetFlow,
        expensesFlow,
    ) { budget, expenses ->
        combineToUiState(budget, expenses)
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(), BudgetDetailUiState(budgetId, "", 0.0, listOf()))

    val budgetHistory: StateFlow<BudgetHistoryUiModel?> = combine(budgetFlow, expensesFlow) { budget, expenses ->
        BudgetHistoryUiModel(
            expenses,
            budgetLimit = budget.limit,
            currentBudgetPeriod = dateTimeProvider.now().toCorrespondingBudgetPeriod(BudgetInterval.Monthly) as BudgetPeriod.Month,
        )
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(), null)

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
            budgetLimit = budget.limit,
            expenses = expenses.divideIntoSections(),
        )
    }

    private val currentYear = dateTimeProvider.today().year

    private fun List<Expense>.divideIntoSections() = this.sortedByDescending {
        it.createdAt
    }.groupBy { expense ->
        // Group expenses by the month (and year) in which they were created
        expense.createdAt.toCorrespondingBudgetPeriod(BudgetInterval.Monthly)
    }.map { (budgetPeriod, expenses) ->
        ExpenseListSection(
            header = if (budgetPeriod is BudgetPeriod.Month) {
                ExpenseListSectionHeader.Month(budgetPeriod.month, budgetPeriod.year, currentYear)
            } else {
                // TODO this is kind of wrong, would have to be adjusted to use the correct budget period if we want to handle all kinds of
                //  budgets correctly here
                ExpenseListSectionHeader.All
            },
            items = expenses.map { ExpenseListItem(it) },
        )
    }
}

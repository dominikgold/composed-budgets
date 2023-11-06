package com.dominikgold.composedbudgets.navigation

import com.dominikgold.composedbudgets.domain.entities.BudgetId

sealed class Destination {

    object BudgetsOverview : Destination()

    data class EditBudget(val budgetId: BudgetId?) : Destination()
}

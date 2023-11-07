package com.dominikgold.composedbudgets.navigation

import com.dominikgold.composedbudgets.domain.entities.BudgetId

sealed class Destination {

    abstract val destinationString: String

    data object BudgetsOverview : Destination() {

        const val route = "budgetsOverview"

        override val destinationString: String = route
    }

    data class EditBudget(val budgetId: BudgetId?) : Destination() {

        override val destinationString = if (budgetId != null) {
            "editBudget?$budgetIdParam=${budgetId.value}"
        } else {
            "editBudget"
        }

        companion object {
            const val budgetIdParam = "budgetId"
            const val route = "editBudget?$budgetIdParam={budgetId}"
        }
    }
}

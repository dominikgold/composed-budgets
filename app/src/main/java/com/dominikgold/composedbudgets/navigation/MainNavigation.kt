package com.dominikgold.composedbudgets.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.dominikgold.composedbudgets.common.collectLifecycleAware
import com.dominikgold.composedbudgets.domain.entities.BudgetId
import com.dominikgold.composedbudgets.features.budgets.EditBudgetUi
import com.dominikgold.composedbudgets.features.overview.BudgetsOverviewUi

@Composable
fun MainNavigation(navigationEvents: NavigationEvents) {
    val navController = rememberNavController()

    navigationEvents.navEvents.collectLifecycleAware {
        navController.navigate(it.destinationString)
    }
    navigationEvents.goBackEvents.collectLifecycleAware {
        navController.popBackStack()
    }

    NavHost(navController = navController, startDestination = Destination.BudgetsOverview.route) {
        composable(Destination.BudgetsOverview.route) {
            BudgetsOverviewUi()
        }
        composable(
            Destination.EditBudget.route,
            arguments = listOf(
                navArgument(Destination.EditBudget.budgetIdParam) {
                    type = NavType.StringType
                    nullable = true
                    defaultValue = null
                }
            )
        ) { backStackEntry ->
            val budgetId = backStackEntry.arguments?.getString(Destination.EditBudget.budgetIdParam)
                ?.let { BudgetId(it) }
            EditBudgetUi(budgetId)
        }
    }
}

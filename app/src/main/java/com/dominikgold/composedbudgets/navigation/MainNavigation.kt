package com.dominikgold.composedbudgets.navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import androidx.navigation.navDeepLink
import com.dominikgold.composedbudgets.common.collectLifecycleAware
import com.dominikgold.composedbudgets.domain.entities.BudgetId
import com.dominikgold.composedbudgets.features.budgets.detail.BudgetDetailUi
import com.dominikgold.composedbudgets.features.budgets.edit.EditBudgetUi
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
            Destination.BudgetDetail.route,
            arguments = listOf(
                navArgument(Destination.BudgetDetail.budgetIdParam) {
                    type = NavType.StringType
                }
            )
        ) { backStackEntry ->
            val budgetId = backStackEntry.arguments?.getString(Destination.BudgetDetail.budgetIdParam)
                ?.let { BudgetId(it) } ?: error("No budget id provided")
            BudgetDetailUi(budgetId)
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

        composable("nonsense/{testArgument}", deepLinks = listOf(navDeepLink { uriPattern = "budgets://test123?testArgument={testArgument}" })) {
            val argument = it.arguments?.getString("testArgument") ?: "no argument"
            TestScreen(argument)
        }
    }
}

@Composable
fun TestScreen(argument: String) {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text(text = "Hello, $argument!", style = MaterialTheme.typography.headlineLarge)
    }
}

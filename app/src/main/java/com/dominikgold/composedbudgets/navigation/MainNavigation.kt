package com.dominikgold.composedbudgets.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.dominikgold.composedbudgets.features.budgets.EditBudgetUi
import com.dominikgold.composedbudgets.features.overview.BudgetsOverviewUi

@Composable
fun MainNavigation() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Destination.BudgetsOverview::class.toString()) {
        composable(Destination.BudgetsOverview::class.toString()) {
            BudgetsOverviewUi()
        }
        composable(Destination.EditBudget::class.toString()) {
            EditBudgetUi()
        }
    }
}

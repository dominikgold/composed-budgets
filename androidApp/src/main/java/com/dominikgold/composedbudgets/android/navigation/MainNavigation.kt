package com.dominikgold.composedbudgets.android.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.compositionLocalOf
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.dominikgold.composedbudgets.android.features.expenses.inmonth.ExpensesInMonthDestination
import com.dominikgold.composedbudgets.android.features.home.HomeScreenDestination
import com.dominikgold.composedbudgets.android.utils.collectActivityLifecycleAware
import com.dominikgold.composedbudgets.navigation.AddExpenses
import com.dominikgold.composedbudgets.navigation.AssignMerchantCategory
import com.dominikgold.composedbudgets.navigation.Destination
import com.dominikgold.composedbudgets.navigation.ExpensesInMonth
import com.dominikgold.composedbudgets.navigation.Home
import com.dominikgold.composedbudgets.navigation.Navigator

@Composable
fun MainNavigation(navigator: Navigator) {
    val navController = rememberNavController()

    navigator.navigationEvents.collectActivityLifecycleAware { destination ->
        if (destination.isBottomSheet()) {
            navController.navigate(destination::class.toString())
        } else {
            navController.navigate(destination)
        }
    }

    navigator.goBackEvents.collectActivityLifecycleAware {
        navController.popBackStack()
    }

    CompositionLocalProvider(LocalNavController provides navController) {
        NavHost(
            navController = navController,
            startDestination = Home,
        ) {
            mainNavGraph()
        }
    }
}

private fun NavGraphBuilder.mainNavGraph() {
    composable<Home> {
        NavDestinationWrapper(it) {
            HomeScreenDestination()
        }
    }
    composable<ExpensesInMonth> {
        NavDestinationWrapper(it) {
            ExpensesInMonthDestination()
        }
    }
}

@Composable
private fun NavDestinationWrapper(backStackEntry: NavBackStackEntry, content: @Composable () -> Unit) {
    CompositionLocalProvider(value = LocalBackStackEntry provides backStackEntry) {
        content()
    }
}

val LocalBackStackEntry = compositionLocalOf<NavBackStackEntry> { error("No BackStackEntry provided") }

val LocalNavController = compositionLocalOf<NavController> { error("No NavController provided") }

private fun Destination.isBottomSheet() = when (this) {
    AddExpenses, AssignMerchantCategory -> true
    else -> false
}

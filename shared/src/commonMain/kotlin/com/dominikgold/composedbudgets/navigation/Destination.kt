package com.dominikgold.composedbudgets.navigation

import kotlinx.serialization.Serializable

sealed interface Destination

@Serializable
data object Home : Destination

@Serializable
data object ExpensesInMonth : Destination

@Serializable
data object AddExpenses : Destination

@Serializable
data object AssignMerchantCategory : Destination

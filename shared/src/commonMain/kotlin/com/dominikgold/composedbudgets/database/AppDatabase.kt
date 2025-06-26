package com.dominikgold.composedbudgets.database

class AppDatabase(databaseDriverFactory: DatabaseDriverFactory) {

    val instance = ComposedBudgetsDatabase(
        databaseDriverFactory.createDriver(),
//        expenseAdapter = Expense.Adapter(
//            dateAdapter = instantAdapter,
//            recurring_periodAdapter = recurringExpensePeriodAdapter,
//        ),
    )
}

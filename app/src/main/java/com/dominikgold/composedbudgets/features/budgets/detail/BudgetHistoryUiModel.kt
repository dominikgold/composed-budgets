package com.dominikgold.composedbudgets.features.budgets.detail

import com.dominikgold.composedbudgets.common.Percentage
import com.dominikgold.composedbudgets.domain.entities.BudgetInterval
import com.dominikgold.composedbudgets.domain.entities.BudgetPeriod
import com.dominikgold.composedbudgets.domain.entities.Expense
import com.dominikgold.composedbudgets.domain.entities.minus
import com.dominikgold.composedbudgets.domain.entities.toCorrespondingBudgetPeriod

// TODO doesn't work correctly with annual or daily budgets - would have to pass a normalized monthly budget limit
data class BudgetHistoryUiModel(
    val expenses: List<Expense>,
    val budgetLimit: Double,
    private val currentBudgetPeriod: BudgetPeriod.Month,
) {

    private val expensePercentages: List<Percentage> = calculateExpensePercentages()

    val budgetLimitIndicatorPlacement: Percentage = calculateLimitIndicatorPlacement()

    val barChartData: List<BarChartUiModel> = calculateBarChartData()

    private fun calculateExpensePercentages(): List<Percentage> {
        val groupedExpenses = expenses.groupBy {
            it.createdAt.toCorrespondingBudgetPeriod(BudgetInterval.Monthly) as BudgetPeriod.Month
        }
        val expensesInLast6Months = listOf(
            groupedExpenses[currentBudgetPeriod.minus(months = 5)].orEmpty(),
            groupedExpenses[currentBudgetPeriod.minus(months = 4)].orEmpty(),
            groupedExpenses[currentBudgetPeriod.minus(months = 3)].orEmpty(),
            groupedExpenses[currentBudgetPeriod.minus(months = 2)].orEmpty(),
            groupedExpenses[currentBudgetPeriod.minus(months = 1)].orEmpty(),
            groupedExpenses[currentBudgetPeriod].orEmpty(),
        )
        return expensesInLast6Months.map { expenses ->
            Percentage((expenses.sumOf { it.amount } / budgetLimit).toFloat())
        }
    }

    private fun calculateLimitIndicatorPlacement(): Percentage {
        val maxExpensePercentage = expensePercentages.maxBy { it.value }
        return if (maxExpensePercentage.value > 1f) Percentage(1f / maxExpensePercentage.value) else Percentage(1f)
    }

    private fun calculateBarChartData(): List<BarChartUiModel> {
        val maxExpensePercentage = expensePercentages.maxBy { it.value }
        val hasOverdraftedMonth = maxExpensePercentage.value > 1f
        return expensePercentages.map { expensePercentage ->
            val barFillPercentage =
                if (hasOverdraftedMonth) expensePercentage.value / maxExpensePercentage.value else expensePercentage.value
            BarChartUiModel(Percentage(barFillPercentage), showErrorColor = barFillPercentage > budgetLimitIndicatorPlacement.value)
        }
    }
}

data class BarChartUiModel(val barFillPercentage: Percentage, val showErrorColor: Boolean)

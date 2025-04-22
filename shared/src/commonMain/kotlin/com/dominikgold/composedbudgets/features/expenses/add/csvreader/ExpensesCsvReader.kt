package com.dominikgold.composedbudgets.features.expenses.add.csvreader

import com.dominikgold.composedbudgets.utils.files.LocalFileResource

interface ExpensesCsvReader {

    suspend fun readExpenses(file: LocalFileResource): List<RawExpenseEntry>
}

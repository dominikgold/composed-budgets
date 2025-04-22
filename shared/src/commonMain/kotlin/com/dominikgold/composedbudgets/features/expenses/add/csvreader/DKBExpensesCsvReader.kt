package com.dominikgold.composedbudgets.features.expenses.add.csvreader

import com.dominikgold.composedbudgets.utils.PlatformContext
import com.dominikgold.composedbudgets.utils.files.LocalFileResource

class DKBExpensesCsvReader(private val context: PlatformContext) : ExpensesCsvReader {
    override suspend fun readExpenses(file: LocalFileResource): List<RawExpenseEntry> {
        TODO("Not yet implemented")
    }
}

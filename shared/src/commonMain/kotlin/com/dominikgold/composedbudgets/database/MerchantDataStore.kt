package com.dominikgold.composedbudgets.database

import com.dominikgold.composedbudgets.entities.ExpenseCategory
import com.dominikgold.composedbudgets.entities.Merchant
import kotlinx.coroutines.flow.Flow

class MerchantDataStore(private val database: AppDatabase) {

    fun getAllMerchants(): Flow<List<Merchant>> {
        TODO()
    }

    fun saveMerchants(merchants: List<Merchant>) {
        TODO()
    }

    fun getAllCategories(): Flow<List<ExpenseCategory>> {
        TODO()
    }

    fun changeMerchantCategory(merchantId: String, category: ExpenseCategory) {
        TODO()
    }

    fun changeMerchantName(merchantId: String, newName: String) {
        TODO()
    }
}

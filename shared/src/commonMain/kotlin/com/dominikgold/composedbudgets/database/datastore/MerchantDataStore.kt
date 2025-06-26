package com.dominikgold.composedbudgets.database.datastore

import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToList
import com.dominikgold.composedbudgets.database.AppDatabase
import com.dominikgold.composedbudgets.entities.MerchantCategory
import com.dominikgold.composedbudgets.entities.Merchant
import kotlinx.coroutines.flow.Flow

class MerchantDataStore(private val database: AppDatabase) {

    fun getAllMerchants(): Flow<List<Merchant>> {
        return database.instance.composedBudgetsDatabaseQueries
            .getAllMerchants { id, original_name, given_name, _, category_id, category_name ->
                Merchant(
                    id = id,
                    originalName = original_name,
                    givenName = given_name,
                    category = MerchantCategory(
                        id = category_id,
                        name = category_name
                    )
                )
            }
            .asFlow()
            .mapToList(dispatcherProvider.io())
    }

    fun saveMerchants(merchants: List<Merchant>) {
        TODO()
    }

    fun getAllCategories(): Flow<List<MerchantCategory>> {
        TODO()
    }

    fun changeMerchantCategory(merchantId: String, category: MerchantCategory) {
        TODO()
    }

    fun changeMerchantName(merchantId: String, newName: String) {
        TODO()
    }
}

package com.dominikgold.composedbudgets.database

import com.dominikgold.composedbudgets.entities.Merchant as MerchantEntity

fun Merchant.toEntity() = MerchantEntity(
    id = this.id,
    originalName = this.original_name,
    givenName = this.given_name,
    category = this.category_id
)

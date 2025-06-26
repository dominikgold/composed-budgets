package com.dominikgold.composedbudgets.entities

data class Merchant(
    val id: String,
    val originalName: String,
    val givenName: String,
    val category: MerchantCategory,
)

package com.dominikgold.composedbudgets.entities

data class MerchantCategory(
    val id: String,
    val name: String,
) {
    companion object {
        val preview = MerchantCategory("1", "Food")
    }
}

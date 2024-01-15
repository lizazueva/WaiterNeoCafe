package com.neobis.waiterneocafe.model.menu

data class CheckPosition(
    val is_ready_made_product: Boolean,
    val item_id: Int,
    val quantity: Int
)
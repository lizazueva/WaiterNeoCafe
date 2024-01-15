package com.neobis.waiterneocafe.model.order

data class CreateOrder(
    val in_an_institution: Boolean,
    val items: List<Item>,
    val spent_bonus_points: Int?,
    val total_price: Int,
    val table_number: Int?
) {
    data class Item(
        val item_id: Int,
        val quantity: Int,
        val is_ready_made_product: Boolean
    )
}

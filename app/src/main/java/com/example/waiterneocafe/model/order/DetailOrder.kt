package com.example.waiterneocafe.model.order

data class DetailOrder(
    val order: Order
) {
    data class Order(
        val branch_name: String,
        val created_at: String,
        val exact_time: String,
        val id: Int,
        val in_an_institution: Boolean,
        val items: List<Item>,
        val spent_bonus_points: Int,
        val status: String,
        val table: Int,
        val total_price: String,
        val waiter_name: String
    ) {
        data class Item(
            val id: Int,
            val item_category: String,
            val item_id: Int,
            val item_image: String?,
            val item_name: String,
            val item_price: Double,
            val item_total_price: Double,
            val is_ready_made_product: Boolean,
            var quantity: Int
        )
    }
}
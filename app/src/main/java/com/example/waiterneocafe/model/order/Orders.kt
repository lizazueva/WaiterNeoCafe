package com.example.waiterneocafe.model.order

import java.io.Serializable

data class Orders(
    val orders: List<Order>
): Serializable {
    data class Order(
        val order_created_at: String,
        val order_number: Int,
        val order_status: String,
        val table_number: Int
    ): Serializable
}
package com.example.waiterneocafe.model.menu

import java.io.Serializable


data class Products (
        val category: Category,
        val compositions: List<Any>?,
        val description: String,
        val id: Int,
        val image: String?,
        val is_available: Boolean,
        val is_ready_made_product: Boolean,
        val name: String,
        val price: String,
        var quantity: Int?,
        var quantityForCard: Int
    ): Serializable {
        data class Category(
            val id: Int,
            val image: String?,
            val name: String
        )
    }
package com.example.waiterneocafe.utils

import com.example.waiterneocafe.model.menu.Products
import com.example.waiterneocafe.model.menu.SearchResultResponse

object OrderUtils {
    private val orderItems: MutableList<Products> = mutableListOf()

    fun addItem(product: Products) {
        val existingItem = orderItems.find { it.id == product.id }

        if (existingItem != null) {
            existingItem.quantityForCard++
        } else {
            val newItem = product.copy(quantityForCard = 1)
            orderItems.add(newItem)
        }
    }

    fun removeItem(product: Products) {
        val existingItem = orderItems.find { it.id == product.id }

        if (existingItem != null) {
            if (existingItem.quantityForCard > 1) {
                existingItem.quantityForCard--
            } else {
                orderItems.remove(existingItem)
            }
        }
    }
    fun addItem(product: SearchResultResponse) {
        val existingItem = orderItems.find { it.id == product.id }

        if (existingItem != null) {
            existingItem.quantityForCard++
        } else {
            val newItem = Products(
                category = Products.Category(id = 1, image = null, name = product.category_name),
                compositions = emptyList(),
                description = product.description,
                id = product.id,
                image = product.image,
                is_available = true,
                name = product.name,
                price = product.price.toString(),
                is_ready_made_product = product.is_ready_made_product,
                quantity = null,
                quantityForCard = 1
            )
            orderItems.add(newItem)
        }
    }
    fun removeItem(product: SearchResultResponse) {
        val existingItem = orderItems.find { it.id == product.id }

        if (existingItem != null) {
            if (existingItem.quantityForCard > 1) {
                existingItem.quantityForCard--
            } else {
                orderItems.remove(existingItem)
            }
        }
    }


    fun getCartItems(): List<Products>{
        return orderItems.toList()
    }


    fun clearCartItems() {
        orderItems.clear()
    }

    fun isInCart(productId: Int): Boolean {
        return orderItems.any { it.id == productId }
    }

    fun getQuantity(productId: Int): Int {
        return orderItems.find { it.id == productId }?.quantityForCard ?: 0
    }
}
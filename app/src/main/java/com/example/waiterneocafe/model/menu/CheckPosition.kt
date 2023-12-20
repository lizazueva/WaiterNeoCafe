package com.example.waiterneocafe.model.menu

import com.example.clientneowaiter.databinding.BottomSheetOrderBinding

data class CheckPosition(
    val is_ready_made_product: Boolean,
    val item_id: Int,
    val quantity: Int
)
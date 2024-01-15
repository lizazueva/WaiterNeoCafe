package com.neobis.waiterneocafe.model.user

class Shedule (
        val id: Int,
        val title: String,
        val workdays: List<Workday>
    ) {
        data class Workday(
            val end_time: String,
            val id: Int,
            val start_time: String,
            val workday: Int
        )
    }
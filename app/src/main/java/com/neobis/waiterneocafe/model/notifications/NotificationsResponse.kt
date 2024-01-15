package com.neobis.waiterneocafe.model.notifications

data class NotificationsResponse(
    val notifications: List<Notifications>) {

    data class Notifications(
        val id: Int,
        var title: String,
        var body: String,
        val exactly_time: String,
        val created_at: String
    )
}

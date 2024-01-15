package com.neobis.waiterneocafe.model.user

data class UserInfo(
    val birth_date: String,
    val bonus: Int?,
    val first_name: String,
    val last_name: String?,
    val phone_number: String,
    val username: String
)
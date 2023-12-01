package com.example.waiterneocafe.model.login

data class ConfirmLoginResponse(
    val phone_number: String,
    val refresh: String,
    val access: String,
    val detail: String
)

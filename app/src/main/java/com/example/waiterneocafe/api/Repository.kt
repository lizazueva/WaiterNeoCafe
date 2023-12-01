package com.example.waiterneocafe.api

import com.example.waiterneocafe.model.login.CodeAuth
import com.example.waiterneocafe.model.login.LoginRequest

class Repository(private val api: Api) {
    suspend fun login(request: LoginRequest) = RetrofitInstance.api.login(request)
    suspend fun confirmLogin(pre_token: String, request: CodeAuth) =
        RetrofitInstance.api.confirmLogin(pre_token, request)
    suspend fun resendCode() = RetrofitInstance.api.resendCode()
}
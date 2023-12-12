package com.example.waiterneocafe.api

import com.example.waiterneocafe.model.login.CodeAuth
import com.example.waiterneocafe.model.login.LoginRequest

class Repository(private val api: Api) {
    suspend fun login(request: LoginRequest) = RetrofitInstance.api.login(request)
    suspend fun confirmLogin(pre_token: String, request: CodeAuth) =
        RetrofitInstance.api.confirmLogin(pre_token, request)
    suspend fun resendCode(pre_token: String) = RetrofitInstance.api.resendCode(pre_token)
    suspend fun getCategories() = RetrofitInstance.api.getCategories()
    suspend fun getMenuCategory(id: Int) = RetrofitInstance.api.getMenuCategory(id)
    suspend fun getSearchResult(q: String) = RetrofitInstance.api.getSearchResult(q)



}
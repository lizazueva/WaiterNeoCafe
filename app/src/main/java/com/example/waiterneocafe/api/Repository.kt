package com.example.waiterneocafe.api

import com.example.waiterneocafe.model.login.CodeAuth
import com.example.waiterneocafe.model.login.LoginRequest
import com.example.waiterneocafe.model.menu.CheckPosition
import com.example.waiterneocafe.model.user.Shedule
import retrofit2.Response

class Repository(private val api: Api) {
    suspend fun login(request: LoginRequest) = RetrofitInstance.api.login(request)
    suspend fun confirmLogin(pre_token: String, request: CodeAuth) =
        RetrofitInstance.api.confirmLogin(pre_token, request)
    suspend fun resendCode(pre_token: String) = RetrofitInstance.api.resendCode(pre_token)
    suspend fun getCategories() = RetrofitInstance.api.getCategories()
    suspend fun getMenuCategory(id: Int) = RetrofitInstance.api.getMenuCategory(id)
    suspend fun getSearchResult(q: String) = RetrofitInstance.api.getSearchResult(q)
    suspend fun getProfile() = RetrofitInstance.api.getProfile()
    suspend fun getShedule() = RetrofitInstance.api.getShedule()
    fun checkPosition(request: CheckPosition) = RetrofitInstance.api.checkPosition(request)


}
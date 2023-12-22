package com.example.waiterneocafe.api

import com.example.waiterneocafe.model.MessageResponse
import com.example.waiterneocafe.model.Table
import com.example.waiterneocafe.model.login.CodeAuth
import com.example.waiterneocafe.model.login.DetailRequest
import com.example.waiterneocafe.model.login.LoginRequest
import com.example.waiterneocafe.model.menu.CheckPosition
import com.example.waiterneocafe.model.menu.Products
import com.example.waiterneocafe.model.order.CreateOrder
import com.example.waiterneocafe.model.order.Orders
import com.example.waiterneocafe.model.user.Shedule
import com.example.waiterneocafe.model.user.UserUpdate
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Query

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
    suspend fun updateProfile(request: UserUpdate) = RetrofitInstance.api.updateProfile(request)
    suspend fun getTables() = RetrofitInstance.api.getTables()
    suspend fun createOrder(request: CreateOrder) = RetrofitInstance.api.createOrder(request)
    fun getOrders() = RetrofitInstance.api.getOrders()
    suspend fun getDetailOrder(id: Int) = RetrofitInstance.api.getDetailOrder(id)
    suspend fun addItemToOrder(orderId: Int,
                               itemId: Int,
                               ready: Boolean,
                               quantity: Int) = RetrofitInstance.api.addItemToOrder(orderId, itemId, ready, quantity)

    suspend fun deleteItemToOrder(orderId: Int) = RetrofitInstance.api.deleteItemToOrder(orderId)









}
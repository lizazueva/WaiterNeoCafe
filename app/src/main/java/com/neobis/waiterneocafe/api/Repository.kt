package com.neobis.waiterneocafe.api

import com.neobis.waiterneocafe.model.login.CodeAuth
import com.neobis.waiterneocafe.model.login.LoginRequest
import com.neobis.waiterneocafe.model.menu.CheckPosition
import com.neobis.waiterneocafe.model.order.CreateOrder
import com.neobis.waiterneocafe.model.user.UserUpdate

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
    fun addItemToOrder(orderId: Int,
                               itemId: Int,
                               ready: Boolean,
                               quantity: Int) = RetrofitInstance.api.addItemToOrder(orderId, itemId, ready, quantity)

    fun deleteItemToOrder(orderId: Int) = RetrofitInstance.api.deleteItemToOrder(orderId)
    suspend fun getIdClient() = RetrofitInstance.api.getIdClient()
    suspend fun deleteNotification(id: Int) = RetrofitInstance.api.deleteNotification(id)
    suspend fun deleteAllNotification(id: Int) = RetrofitInstance.api.deleteAllNotification(id)
    suspend fun completeOrder(orderId: Int) = RetrofitInstance.api.completeOrder(orderId)
    suspend fun getOrderDetail(id: Int) = RetrofitInstance.api.getDetailOrder1(id)












}
package com.example.waiterneocafe.api

import com.example.waiterneocafe.model.MessageResponse
import com.example.waiterneocafe.model.Table
import com.example.waiterneocafe.model.TableResponse
import com.example.waiterneocafe.model.login.CodeAuth
import com.example.waiterneocafe.model.login.ConfirmLoginResponse
import com.example.waiterneocafe.model.login.DetailRequest
import com.example.waiterneocafe.model.login.LoginRequest
import com.example.waiterneocafe.model.login.LoginResponse
import com.example.waiterneocafe.model.menu.CheckPosition
import com.example.waiterneocafe.model.menu.Products
import com.example.waiterneocafe.model.menu.SearchResultResponse
import com.example.waiterneocafe.model.order.CreateOrder
import com.example.waiterneocafe.model.order.DetailOrder
import com.example.waiterneocafe.model.order.Orders
import com.example.waiterneocafe.model.user.Shedule
import com.example.waiterneocafe.model.user.UserInfo
import com.example.waiterneocafe.model.user.UserUpdate
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

interface Api {

    @POST("accounts/login-waiter/")
    suspend fun login (@Body request: LoginRequest): Response<LoginResponse>

    //test
    @POST("accounts/temporary-login-waiter/")
    suspend fun login2 (@Body request: LoginRequest): Response<ConfirmLoginResponse>

    @POST("accounts/confirm-login/")
    suspend fun confirmLogin (@Header("Authorization") pre_token: String, @Body request: CodeAuth): Response<ConfirmLoginResponse>
    @GET("accounts/resend-code-with-pre-token/")
    suspend fun resendCode(@Header("Authorization") pre_token: String): Response<DetailRequest>
    @GET("accounts/my-profile/")
    suspend fun getProfile(): Response<UserInfo>
    @GET("accounts/my-schedule/")
    suspend fun getShedule(): Response<Shedule>
    @PUT("accounts/update-waiter-profile/")
    suspend fun updateProfile(@Body request: UserUpdate): Response<DetailRequest>




    @GET("customers/categories/")
    suspend fun getCategories(): Response<List<Products.Category>>
    @GET("customers/menu")
    suspend fun getMenuCategory(@Query("category_id") id: Int): Response<List<Products>>

    @GET("customers/search")
    suspend fun getSearchResult(@Query("query") q: String): Response<List<SearchResultResponse>>
    @POST("customers/check-if-item-can-be-made/")
    fun checkPosition(@Body request: CheckPosition): Call<MessageResponse>

    @GET("waiter/get-table-availibility/")
    suspend fun getTables(): Response<TableResponse>
    @POST("ordering/create-order/")
    suspend fun createOrder(@Body request: CreateOrder): Response<CreateOrder>
    @GET("waiter/get-orders-in-institution/")
    fun getOrders(): Call<Orders>
    @GET("waiter/get-table-detail/")
    suspend fun getDetailOrder(@Query("table_number") id: Int): Response<DetailOrder>

    @POST("ordering/add-item-to-order/")
    suspend fun addItemToOrder(@Query("order_id") orderId: Int,
                               @Query("item_id") itemId: Int,
                               @Query("is_ready_made_product") ready: Boolean,
                               @Query("item_id") quantity: Int): Response<MessageResponse>

    @DELETE("ordering/remove-order-item/")
    suspend fun deleteItemToOrder(@Query("order_item_id") orderId: Int): Response<MessageResponse>






}
package com.example.waiterneocafe.api

import com.example.waiterneocafe.model.MessageResponse
import com.example.waiterneocafe.model.login.CodeAuth
import com.example.waiterneocafe.model.login.ConfirmLoginResponse
import com.example.waiterneocafe.model.login.DetailRequest
import com.example.waiterneocafe.model.login.LoginRequest
import com.example.waiterneocafe.model.login.LoginResponse
import com.example.waiterneocafe.model.menu.CheckPosition
import com.example.waiterneocafe.model.menu.Products
import com.example.waiterneocafe.model.menu.SearchResultResponse
import com.example.waiterneocafe.model.user.Shedule
import com.example.waiterneocafe.model.user.UserInfo
import com.example.waiterneocafe.model.user.UserUpdate
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
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


}
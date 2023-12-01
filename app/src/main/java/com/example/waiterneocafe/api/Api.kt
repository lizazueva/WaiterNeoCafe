package com.example.waiterneocafe.api

import com.example.waiterneocafe.model.login.CodeAuth
import com.example.waiterneocafe.model.login.ConfirmLoginResponse
import com.example.waiterneocafe.model.login.DetailRequest
import com.example.waiterneocafe.model.login.LoginRequest
import com.example.waiterneocafe.model.login.LoginResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface Api {

    @POST("accounts/login-for-client/")
    suspend fun login (@Body request: LoginRequest): Response<LoginResponse>

    //test
    @POST("accounts/temporary-login-waiter/")
    suspend fun login2 (@Body request: LoginRequest): Response<ConfirmLoginResponse>

    @POST("accounts/confirm-login/")
    suspend fun confirmLogin (@Header("Authorization") pre_token: String, @Body request: CodeAuth): Response<ConfirmLoginResponse>
    @GET("accounts/resend-code-with-per-token/")
    suspend fun resendCode(): Response<DetailRequest>

}
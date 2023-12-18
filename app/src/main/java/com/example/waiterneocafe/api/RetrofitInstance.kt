package com.example.waiterneocafe.api

import com.example.waiterneocafe.utils.Constants.Companion.BASE_URL
import com.example.waiterneocafe.utils.Utils
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitInstance {
    companion object{
        private val interceptor = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

        private val client = OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .addInterceptor(AuthorizationInterceptor())
            .build()

        val retrofit by lazy {
            Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }

        val api:Api by lazy {
            retrofit.create(Api::class.java)
        }

        private class AuthorizationInterceptor: Interceptor {
            override fun intercept(chain: Interceptor.Chain): Response {
                val request = chain.request()
                val newRequest = if (requiresAuthorization(request)){
                    val token = Utils.access_token
                    val authHeader = "Bearer $token"
                    request.newBuilder()
                        .header("Authorization", authHeader)
                        .build()
                }else{
                    request
                }
                return chain.proceed(newRequest)
            }
        }

        private fun requiresAuthorization(request: okhttp3.Request): Boolean {
            val path = request.url.encodedPath
            return path.endsWith("my-profile/")||
                    path.endsWith("resend-code/")||
                    path.endsWith("edit-profile/")||
                    path.contains("customers/menu") && request.method == "GET"||
                    path.endsWith("customers/categories/")||
                    path.contains("customers/search") && request.method == "GET"||
                    path.endsWith("accounts/my-schedule/")
        }
    }

}
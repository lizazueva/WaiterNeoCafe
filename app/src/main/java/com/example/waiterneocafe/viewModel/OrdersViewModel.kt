package com.example.waiterneocafe.viewModel

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.waiterneocafe.api.Repository
import com.example.waiterneocafe.model.order.Orders
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class OrdersViewModel(private val repository: Repository): ViewModel() {

    fun getOrders(
        onSuccess: (List<Orders.Order>) -> Unit,
        onError: (String?) -> Unit
    ) {
        repository.getOrders().enqueue(object : Callback<Orders> {
            override fun onResponse(call: Call<Orders>, response: Response<Orders>) {
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    responseBody?.let {
                        onSuccess(it.orders) // Передаем данные успешного ответа в колбэк
                    }
                    Log.d("getOrders", "Successful: $responseBody")
                } else {
                    val errorBody = response.errorBody()?.toString()
                    onError(errorBody ?: "Ошибка получения заказов") // Передаем сообщение об ошибке в колбэк
                }
            }

            override fun onFailure(call: Call<Orders>, t: Throwable) {
                Log.e("MyViewModel", "Ошибка загрузки", t)
                onError(t.message ?: "Ошибка загрузки") // Передаем сообщение об ошибке в колбэк
            }
        })
    }


}


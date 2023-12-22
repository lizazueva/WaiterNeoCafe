package com.example.waiterneocafe.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.waiterneocafe.api.Repository
import com.example.waiterneocafe.model.order.DetailOrder
import com.example.waiterneocafe.model.order.Orders
import com.example.waiterneocafe.utils.Resource
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class OrdersViewModel(private val repository: Repository): ViewModel() {

    //получение детальной информации о продукте
    private val _detailOrder: MutableLiveData<Resource<DetailOrder>> = MutableLiveData()
    val detailProduct: LiveData<Resource<DetailOrder>>
        get() = _detailOrder

    private fun saveDetailOrder(response: DetailOrder) {
        _detailOrder.postValue(Resource.Success(response))
    }

    fun orderDetail(id: Int) {
        viewModelScope.launch {
            _detailOrder.postValue(Resource.Loading())
            try {
                val response = repository.getDetailOrder(id)
                if (response.isSuccessful) {
                    val productResponse = response.body()
                    productResponse?.let { saveDetailOrder(it) }
                    Log.d("orderDetail", "Successful: $productResponse")
                }else{
                    val errorBody = response.errorBody()?.toString()
                    _detailOrder.postValue(Resource.Error(errorBody ?:"Ошибка загрузки товарa"))
                }
            } catch (e: Exception) {
                Log.e("MyViewModel", "Ошибка загрузки: ${e.message}")
                _detailOrder.postValue(Resource.Error(e.message ?: "Ошибка загрузки"))
            }
        }
    }

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


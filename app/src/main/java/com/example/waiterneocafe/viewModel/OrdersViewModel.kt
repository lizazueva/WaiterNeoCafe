package com.example.waiterneocafe.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.waiterneocafe.api.Repository
import com.example.waiterneocafe.model.MessageResponse
import com.example.waiterneocafe.model.order.CreateOrder
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

    //добавление позиции в заказ
    private val _addOrder: MutableLiveData<Resource<MessageResponse>?> = MutableLiveData()
    val addOrder: MutableLiveData<Resource<MessageResponse>?>
        get() = _addOrder
    private fun saveAddOrder(response: MessageResponse) {
        _addOrder.postValue(Resource.Success(response))
    }

    //удаление позиции в заказ
    private val _resultDelete: MutableLiveData<Resource<MessageResponse>?> = MutableLiveData()
    val resultDelete: MutableLiveData<Resource<MessageResponse>?>
        get() = _resultDelete
    private fun saveResultDelete(response: MessageResponse) {
        _resultDelete.postValue(Resource.Success(response))
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

    fun addItemToOrder (orderId: Int,
                        itemId: Int,
                        ready: Boolean,
                        quantity: Int){
        viewModelScope.launch {
            try {
                val response = repository.addItemToOrder(orderId, itemId, ready, quantity)
                if (response.isSuccessful) {
                    _addOrder.postValue(Resource.Loading())
                    val orderResponse = response.body()
                    orderResponse?.let { saveAddOrder(it) }
                    Log.d("addItemToOrder", "Successful: $orderResponse")
                }else{
                    _addOrder.postValue(Resource.Error("Ошибка добавления позиции"))
                    Log.e("MyViewModel", "createOrder Error: ${response.errorBody()?.toString()}")
                }
            } catch (e: Exception) {
                Log.e("MyViewModel", "Ошибка добавления позиции: ${e.message}")

                _addOrder.postValue(Resource.Error(e.message ?: "Ошибка добавления позиции"))
            }
        }
    }

    fun deleteItemToOrder(orderId: Int){
        viewModelScope.launch {
            try {
                val response = repository.deleteItemToOrder(orderId)
                if (response.isSuccessful) {
                    _resultDelete.postValue(Resource.Loading())
                    val deleteResponse = response.body()
                    deleteResponse?.let { saveResultDelete(it) }
                    Log.d("deleteItemToOrder", "Successful: $deleteResponse")
                }else{
                    _resultDelete.postValue(Resource.Error("Ошибка добавления позиции"))
                    Log.e("MyViewModel", "Error: ${response.errorBody()?.toString()}")
                }
            } catch (e: Exception) {
                Log.e("MyViewModel", "Ошибка добавления позиции: ${e.message}")
                _resultDelete.postValue(Resource.Error(e.message ?: "Ошибка добавления позиции"))
            }
        }
    }


}


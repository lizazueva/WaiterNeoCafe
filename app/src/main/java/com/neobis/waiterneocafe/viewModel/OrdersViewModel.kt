package com.neobis.waiterneocafe.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.neobis.waiterneocafe.api.Repository
import com.neobis.waiterneocafe.model.MessageResponse
import com.neobis.waiterneocafe.model.order.DetailOrder
import com.neobis.waiterneocafe.model.order.Orders
import com.neobis.waiterneocafe.utils.Resource
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

    //удаление позиции в заказ
    private val _resultDelete: MutableLiveData<Resource<MessageResponse>?> = MutableLiveData()
    val resultDelete: MutableLiveData<Resource<MessageResponse>?>
        get() = _resultDelete
    private fun saveResultDelete(response: MessageResponse) {
        _resultDelete.postValue(Resource.Success(response))
    }

    //закрытие счета
    private val _completeOrder: MutableLiveData<Resource<MessageResponse>> = MutableLiveData()
    val completeOrder: MutableLiveData<Resource<MessageResponse>>
        get() = _completeOrder
    private fun saveCompleteOrder(response: MessageResponse) {
        _completeOrder.postValue(Resource.Success(response))
    }


    fun orderDetail(id: Int) {
        viewModelScope.launch {
            _detailOrder.postValue(Resource.Loading())
            try {
                val response = repository.getOrderDetail(id)
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


    fun addItemOrder(
        orderId: Int,
        itemId: Int,
        ready: Boolean,
        quantity: Int,
        onSuccess: () -> Unit,
        onError: (String?) -> Unit
    ) {
        repository.addItemToOrder(orderId, itemId, ready, quantity)
            .enqueue(object : Callback<MessageResponse> {
                override fun onResponse(
                    call: Call<MessageResponse>,
                    response: Response<MessageResponse>
                ) {
                    if (response.isSuccessful) {
                        onSuccess()
                    } else {
                        onError("Ошибка при выполнении запроса: ${response.code()}")
                    }
                }

                override fun onFailure(call: Call<MessageResponse>, t: Throwable) {
                    Log.e("addItemOrder", "Ошибка при выполнении запроса", t)
                    onError("")
                }
            })
    }

    fun deleteItemToOrder(
        orderId: Int,
        onSuccess: () -> Unit,
        onError: (String?) -> Unit
    ) {
        repository.deleteItemToOrder(orderId)
            .enqueue(object : Callback<MessageResponse> {
                override fun onResponse(
                    call: Call<MessageResponse>,
                    response: Response<MessageResponse>
                ) {
                    if (response.isSuccessful) {
                        onSuccess()
                    } else {
                        onError("Ошибка при выполнении запроса: ${response.code()}")
                    }
                }

                override fun onFailure(call: Call<MessageResponse>, t: Throwable) {
                    Log.e("addItemOrder", "Ошибка при выполнении запроса", t)
                    onError("")
                }
            })
    }

    fun completeOrder(orderId: Int){
        viewModelScope.launch {
            _completeOrder.postValue(Resource.Loading())
            try {
                val response = repository.completeOrder(orderId)
                if (response.isSuccessful) {
                    val productResponse = response.body()
                    productResponse?.let { saveCompleteOrder(it) }
                    Log.d("completeOrder", "Successful: $productResponse")
                }else{
                    val errorBody = response.errorBody()?.toString()
                    _completeOrder.postValue(Resource.Error(errorBody ?:"Ошибка закрытия счета"))
                }
            } catch (e: Exception) {
                Log.e("MyViewModel", "Ошибка загрузки: ${e.message}")
                _completeOrder.postValue(Resource.Error(e.message ?: "Ошибка загрузки"))
            }
        }
    }
}


package com.neobis.waiterneocafe.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.neobis.waiterneocafe.api.Repository
import com.neobis.waiterneocafe.model.TableResponse
import com.neobis.waiterneocafe.model.order.CreateOrder
import com.neobis.waiterneocafe.utils.Resource
import kotlinx.coroutines.launch

class NewOrderViewModel(private val repository: Repository): ViewModel() {
    //получение списка столов
    private val _table: MutableLiveData<Resource<TableResponse>> = MutableLiveData()
    val table: LiveData<Resource<TableResponse>>
        get() = _table

    private fun saveTable(response: TableResponse) {
        _table.postValue(Resource.Success(response))
    }

    //создание заказа
    private val _order: MutableLiveData<Resource<CreateOrder>?> = MutableLiveData()
    val order: MutableLiveData<Resource<CreateOrder>?>
        get() = _order
    private fun saveOrder(response: CreateOrder) {
        _order.postValue(Resource.Success(response))
    }

    fun getTables(){
        viewModelScope.launch {
            _table.postValue(Resource.Loading())
            try {
                val response = repository.getTables()
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    responseBody?.let { saveTable(it) }
                    Log.d("getTables()", "Successful: $responseBody")

                }else{
                    val errorBody = response.errorBody()?.toString()
                    _table.postValue(Resource.Error(errorBody ?: "Ошибка получения столов"))
                }
            } catch (e: Exception) {
                Log.e("MyViewModel", "Ошибка загрузки: ${e.message}")
                _table.postValue(Resource.Error(e.message ?: "Ошибка загрузки"))
            }
        }
    }

    fun createOrder (order: CreateOrder){
        viewModelScope.launch {
            try {
                val response = repository.createOrder(order)
                if (response.isSuccessful) {
                    _order.postValue(Resource.Loading())
                    val orderResponse = response.body()
                    orderResponse?.let { saveOrder(it) }
                    Log.d("createOrder", "Successful: $orderResponse")
                }else{
                    _order.postValue(Resource.Error("Ошибка оформления заказа"))
                    Log.e("MyViewModel", "createOrder Error: ${response.errorBody()?.toString()}")
                }
            } catch (e: Exception) {
                Log.e("MyViewModel", "Ошибка оформления заказа: ${e.message}")

                _order.postValue(Resource.Error(e.message ?: "Ошибка оформления заказа"))
            }
        }
    }

}
package com.example.waiterneocafe.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.waiterneocafe.api.Repository
import com.example.waiterneocafe.model.user.Shedule
import com.example.waiterneocafe.model.user.UserInfo
import com.example.waiterneocafe.utils.Resource
import kotlinx.coroutines.launch

class UserViewModel(private val repository: Repository): ViewModel() {

    private val _user: MutableLiveData<Resource<UserInfo>> = MutableLiveData()

    val user: LiveData<Resource<UserInfo>>
        get() = _user

    private fun saveUser (response: UserInfo){
        _user.postValue(Resource.Success(response))
    }

    //получение расписания

    private val _shedule: MutableLiveData<Resource<Shedule>> = MutableLiveData()

    val shedule: LiveData<Resource<Shedule>>
        get() = _shedule

    private fun saveShedule (response: Shedule){
        _shedule.postValue(Resource.Success(response))
    }

    fun getProfile(){
        viewModelScope.launch {
            try {
                val response = repository.getProfile()
                _user.postValue(Resource.Loading())
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    responseBody?.let { saveUser(it) }
                    Log.d("getProfile", "Successful: $responseBody")
                }else{
                    _user.postValue(Resource.Error("Ошибка получения данных клиента"))
                }
            } catch (e: Exception) {
                Log.e("MyViewModel", "Ошибка получения данных клиента: ${e.message}")

                _user.postValue(Resource.Error(e.message ?: "Ошибка получения данных клиента"))
            }
        }

    }

    fun getShedule(){
        viewModelScope.launch {
            try {
                val response = repository.getShedule()
                _shedule.postValue(Resource.Loading())
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    responseBody?.let { saveShedule(it) }
                    Log.d("getShedule", "Successful: $responseBody")
                }else{
                    val errorBody = response.errorBody()?.toString()
                    _shedule.postValue(Resource.Error(errorBody ?:"Ошибка получения данных клиента"))
                }
            } catch (e: Exception) {
                Log.e("MyViewModel", "Ошибка получения данных клиента: ${e.message}")

                _shedule.postValue(Resource.Error(e.message ?: "Ошибка получения данных клиента"))
            }
        }

    }
}
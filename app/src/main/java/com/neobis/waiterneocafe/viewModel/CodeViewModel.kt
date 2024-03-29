package com.neobis.waiterneocafe.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.neobis.waiterneocafe.api.Repository
import com.neobis.waiterneocafe.model.login.CodeAuth
import com.neobis.waiterneocafe.utils.Resource
import com.neobis.waiterneocafe.utils.Utils
import kotlinx.coroutines.launch

class CodeViewModel (private val repository: Repository): ViewModel() {


    private val _resendCodeResult: MutableLiveData<Resource<String>> = MutableLiveData()
    val resendCodeResult: LiveData<Resource<String>>
        get() = _resendCodeResult


    private val _token: MutableLiveData<Resource<String>> = MutableLiveData()
    val token: LiveData<Resource<String>>
        get() = _token

    private fun saveToken(response: String) {
        _token.postValue(Resource.Success(response))
    }


    fun confirmLogin(code: String) {
        val codeAuth = CodeAuth(code)
        _token.postValue(Resource.Loading())
        viewModelScope.launch {
            try {
                val pre_token = Utils.pre_token
                val request = repository.confirmLogin(pre_token, codeAuth)
                if (request.isSuccessful) {
                    val responseBody = request.body()
                    responseBody?.access?.let { saveToken(it) }
                    if (responseBody != null) {
                        Utils.access_token = responseBody.access
                    }
                    Log.d("confirmPhone", "Successful: $responseBody")
                } else {
                    val errorBody = request.errorBody()?.string()
                    _token.postValue(Resource.Error(errorBody ?: "Ошибка кода"))
                }
            } catch (e: Exception) {
                Log.e("MyViewModel", "Ошибка кода: ${e.message}")
                _token.postValue(Resource.Error(e.message ?: "Ошибка кода"))
            }
        }
    }

    fun confirmLogin2(code: String, onResult: (Boolean) -> Unit) {
        viewModelScope.launch {
            try {
                val pre_token = Utils.pre_token
                val codeAuth = CodeAuth(code)
                val response = repository.confirmLogin(pre_token, codeAuth)
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null) {
                        Utils.access_token = responseBody.access
                    }
                    Log.d("confirmPhone", "Successful: $responseBody")
                    onResult(true)
                } else {
                    onResult(false)
                }
            } catch (e: Exception) {
                Log.e("MyViewModel", "Ошибка кода: ${e.message}")
                onResult(false)
            }
        }
    }

        fun resendCode() {
            _resendCodeResult.postValue(Resource.Loading())
            viewModelScope.launch {
                try {
                    val pre_token = Utils.pre_token
                    val request = repository.resendCode(pre_token)
                    if (request.isSuccessful) {
                        val responseBody = request.body()
                        if (responseBody != null) {
                            _resendCodeResult.postValue(Resource.Success(responseBody.detail))
                        }
                        Log.d("resendCode", "Successful: $responseBody")
                    } else {
                        val errorBody = request.errorBody()?.string()
                        _resendCodeResult.postValue(
                            Resource.Error(errorBody ?: "Ошибка повторной отправки кода")
                        )
                    }
                } catch (e: Exception) {
                    Log.e("MyViewModel", "Ошибка кода: ${e.message}")
                    _resendCodeResult.postValue(
                        Resource.Error(
                            e.message ?: "Ошибка повторной отправки кода"
                        )
                    )
                }
            }
        }
    }
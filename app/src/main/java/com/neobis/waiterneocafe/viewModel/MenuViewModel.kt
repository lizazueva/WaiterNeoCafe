package com.neobis.waiterneocafe.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.neobis.waiterneocafe.api.Repository
import com.neobis.waiterneocafe.model.MessageResponse
import com.neobis.waiterneocafe.model.menu.CheckPosition
import com.neobis.waiterneocafe.model.menu.Products
import com.neobis.waiterneocafe.model.menu.SearchResultResponse
import com.neobis.waiterneocafe.utils.Resource
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MenuViewModel(private val repository: Repository): ViewModel() {
    //получение списка позиций по категориям
    private val _menuCategory: MutableLiveData<Resource<List<Products>>> = MutableLiveData()
    val menuCategory: LiveData<Resource<List<Products>>>
        get() = _menuCategory

    private fun saveMenuCategory(response: List<Products>) {
        _menuCategory.postValue(Resource.Success(response))
    }

    //получение списка категорий для меню
    private val _categories: MutableLiveData<Resource<List<Products.Category>>> = MutableLiveData()

    val categories: LiveData<Resource<List<Products.Category>>>
        get() = _categories

    private fun saveCategories(response: List<Products.Category>) {
        _categories.postValue(Resource.Success(response))
    }

    //получение списка найденных позиций для меню
    private val _searchItems: MutableLiveData<Resource<List<SearchResultResponse>>> = MutableLiveData()

    val searchItems: LiveData<Resource<List<SearchResultResponse>>>
        get() = _searchItems

    private fun saveSearchItems(response: List<SearchResultResponse>) {
        _searchItems.postValue(Resource.Success(response))
    }

    fun createProduct(
        positionCheck: CheckPosition,
        onSuccess: () -> Unit,
        onError: (String?) -> Unit
    ) {
        repository.checkPosition(positionCheck)
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
                    Log.e("AddProductViewModel", "Ошибка при выполнении запроса", t)
                    onError("")
                }
            })
    }

    fun getSearchResult(q: String){
        viewModelScope.launch {
            _searchItems.postValue(Resource.Loading())
            try {
                val response = repository.getSearchResult(q)
                if (response.isSuccessful) {
                    val productResponse = response.body()
                    productResponse?.let { saveSearchItems(it) }
                    Log.d("getSearchResult", "Successful: $productResponse")
                }else{
                    val errorBody = response.errorBody()?.toString()
                    _searchItems.postValue(Resource.Error(errorBody ?:"Ошибка загрузки товаров"))
                }
            } catch (e: Exception) {
                Log.e("MyViewModel", "Ошибка загрузки: ${e.message}")
                _searchItems.postValue(Resource.Error(e.message ?: "Ошибка загрузки"))
            }
        }
    }

    fun getMenuCategory(id: Int) {
        viewModelScope.launch {
            _menuCategory.postValue(Resource.Loading())
            try {
                val response = repository.getMenuCategory(id)
                if (response.isSuccessful) {
                    val productResponse = response.body()
                    productResponse?.let { saveMenuCategory(it) }
                    Log.d("getMenuCategory", "Successful: $productResponse")
                }else{
                    val errorBody = response.errorBody()?.toString()
                    _menuCategory.postValue(Resource.Error(errorBody ?:"Ошибка загрузки товаров"))
                }
            } catch (e: Exception) {
                Log.e("MyViewModel", "Ошибка загрузки: ${e.message}")
                _menuCategory.postValue(Resource.Error(e.message ?: "Ошибка загрузки"))
            }
        }
    }

    fun getCategories(){
        viewModelScope.launch {
            _categories.postValue(Resource.Loading())
            try {
                val response = repository.getCategories()
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    responseBody?.let { saveCategories(it) }
                    Log.d("getCategories", "Successful: $responseBody")

                }else{
                    val errorBody = response.errorBody()?.toString()
                    _categories.postValue(Resource.Error(errorBody ?: "Ошибка получения категорий"))
                }
            } catch (e: Exception) {
                Log.e("MyViewModel", "Ошибка загрузки: ${e.message}")
                _categories.postValue(Resource.Error(e.message ?: "Ошибка загрузки"))
            }
        }
    }

}
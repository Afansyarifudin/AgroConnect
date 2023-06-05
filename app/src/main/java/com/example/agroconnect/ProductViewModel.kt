package com.example.agroconnect


import androidx.lifecycle.*

import kotlinx.coroutines.launch

class ProductViewModel : ViewModel() {
    private val apiService: ApiService = ApiConfig.createApiService()

    private val _products = MutableLiveData<List<Product>>()
    val products: LiveData<List<Product>> get() = _products

    private val _searchResultEmpty = MutableLiveData<Boolean>()
    val searchResultEmpty: LiveData<Boolean> get() = _searchResultEmpty

    fun searchProducts(query: String) {
        viewModelScope.launch {
            try {
                val response = apiService.searchProducts(query)
                val productResponse = response.body()
                val productList = productResponse?.data
                if (productList.isNullOrEmpty()) {
                    _searchResultEmpty.value = true
                    _products.value = productList
                } else {
                    _searchResultEmpty.value = false
                    _products.value = productList
                }
                    // Handle API error
            } catch (e: Exception) {
                // Handle network error
            }
        }
    }
}


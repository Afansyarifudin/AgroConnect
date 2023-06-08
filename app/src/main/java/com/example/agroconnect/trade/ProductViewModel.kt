package com.example.agroconnect.trade


import android.app.Application
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.*
import com.example.agroconnect.Result
import com.example.agroconnect.api.ApiConfig
import com.example.agroconnect.api.ApiService
import com.example.agroconnect.datamodel.*

import kotlinx.coroutines.launch

class ProductViewModel(application: Application) : AndroidViewModel(application) {
    private val apiService: ApiService = ApiConfig.createApiService()

    private val _products = MutableLiveData<List<Product>>()
    val products: LiveData<List<Product>> get() = _products

    private val _demands = MutableLiveData<List<Demand>>()
    val demands: MutableLiveData<List<Demand>> get() = _demands

    private val _productData = MutableLiveData<Product?>()
    val productData: MutableLiveData<Product?> get() = _productData

    private val _demandData = MutableLiveData<Demand?>()
    val demandData: MutableLiveData<Demand?> get() = _demandData

    private val _searchResultEmpty = MutableLiveData<Boolean>()
    val searchResultEmpty: LiveData<Boolean> get() = _searchResultEmpty

    fun searchProducts(query: String) {
        viewModelScope.launch {
            try {
                val response = apiService.searchProducts(query)
                val productResponse = response.body()
                val productList = productResponse!!.data
//                val fullProductResponse = apiService.getDetailProducts(productList[0].categoryId)
                Log.d("ProductViewModel", "Response Category: ${productList[0].categoryId}")
                Log.d("ProductViewModel", "Response: $productList")
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

    fun postProducts(selectedCategory: Int, name: String, amount: String, cropDate: String, expDate: String, location: String, tokenAuth: String) {
        viewModelScope.launch {
            try {
                val request = ProductRequest(
                    name = name,
                    amount = amount.toInt(),
                    location = location,
                    crop_date = cropDate,
                    estimate_exp = expDate,
                    category_id = selectedCategory
                )
                Log.d("ProductViewModel", "Request Add Product: $request, Token: $tokenAuth")

                val response = apiService.postProducts(tokenAuth, request)
                Log.d("ProductViewModel", "Response Add Product: $response")
                val productData = response.body()?.data
                _productData.value = productData
                Log.d("ProductViewModel", "The request should be transferred to Activity")


            } catch (e: Exception) {
                Log.e("ProductViewModel", "Error during API call: ${e.message}", e)
                // Handle the exception
                _productData.value = null
                showToast("Failed to add product.")
            }
        }
    }

    fun postDemand(selectedCategory: Int, name: String, amount: String, location: String, tokenAuth: String) {
        viewModelScope.launch {
            try {
                val request = DemandRequest(
                    name = name,
                    amount = amount.toInt(),
                    location = location,
                    category_id = selectedCategory
                )
                Log.d("ProductViewModel", "Request Add Demand: $request, Token: $tokenAuth")

                val response = apiService.postDemand(tokenAuth, request)
                Log.d("ProductViewModel", "Response Add Demand: $response")
                val demandData = response.body()?.data
                _demandData.value = demandData
                Log.d("ProductViewModel", "The request should be transferred to Activity")


            } catch (e: Exception) {
                Log.e("ProductViewModel", "Error during API call: ${e.message}", e)
                // Handle the exception
                _demandData.value = null
                showToast("Failed to add Demand.")
            }
        }
    }

    fun searchProductsByCategory(query: String, categoryId: Int) {
        viewModelScope.launch {
            try {
                val response = apiService.searchProducts(query)
                val productResponse = response.body()
                val productList = productResponse!!.data
                val filteredList = productList.filter { it.categoryId == categoryId }
                if (filteredList.isNullOrEmpty()) {
                    _searchResultEmpty.value = true
                    _products.value = filteredList
                } else {
                    _searchResultEmpty.value = false
                    _products.value = filteredList
                }
                // Handle API error
            } catch (e: Exception) {
                // Handle network error
            }
        }
    }

    fun searchDemand(query: String) {
        viewModelScope.launch {
            try {
                val response = apiService.searchDemands(query)
                val demandResponse = response.body()
                val demandList = demandResponse!!.data
//                val fullProductResponse = apiService.getDetailProducts(productList[0].categoryId)
                Log.d("ProductViewModel", "Response Category: ${demandList?.get(0)?.categoryId}")
                Log.d("ProductViewModel", "Response: $demandList")
                if (demandList.isNullOrEmpty()) {
                    _searchResultEmpty.value = true
                    _demands.value = demandList
                } else {
                    _searchResultEmpty.value = false
                    _demands.value = demandList
                }
                // Handle API error
            } catch (e: Exception) {
                // Handle network error
            }
        }
    }

    fun searchDemandByCategory(query: String, categoryId: Int) {
        viewModelScope.launch {
            try {
                val response = apiService.searchDemands(query)
                val demandResponse = response.body()
                val demandList = demandResponse!!.data
                val filteredList = demandList.filter { it.categoryId == categoryId }
                if (filteredList.isNullOrEmpty()) {
                    _searchResultEmpty.value = true
                    _demands.value = filteredList
                } else {
                    _searchResultEmpty.value = false
                    _demands.value = filteredList
                }
                // Handle API error
            } catch (e: Exception) {
                // Handle network error
            }
        }
    }

    fun getAllDemand(categoryId: Int, userId: Int) {
        viewModelScope.launch {
            try {
                val response = apiService.getAllDemands()
                val demandAllResponse = response.body()
                val demandAllList = demandAllResponse!!.data
                val filteredList = demandAllList.filter {
                    it.categoryId == categoryId && it.userId == userId
                }

                Log.d("ProductViewModel", "Fetch user $userId and category $categoryId")
                if (filteredList.isNullOrEmpty()) {
                    _demands.value = filteredList
                } else {
                    _demands.value = filteredList
                }


            } catch (e: Exception) {

            }
        }
    }

    private fun showToast(message: String) {
        val context = getApplication<Application>()
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }


}




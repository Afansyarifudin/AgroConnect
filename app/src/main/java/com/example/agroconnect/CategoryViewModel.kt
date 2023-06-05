package com.example.agroconnect

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class CategoryViewModel : ViewModel() {
    private val apiService: ApiService = ApiConfig.createApiService()

    private val _categories = MutableLiveData<List<Category>>()
    val categories: LiveData<List<Category>> get() = _categories

    fun fetchCategories() {
        viewModelScope.launch {
            try {
                val response = apiService.getCategories()
                if (response.isSuccessful) {
                    val categoryResponse = response.body()
                    if (categoryResponse != null) {
                        val categoryList = categoryResponse.data
                        _categories.value = categoryList
                        Log.d("CategoryViewModel", "Categories Fetched Successfully")
                    } else {
                        Log.e("CategoryViewModel", "Empty Response Body")
                    }
                } else {
                    Log.e("CategoryViewModel", "Error: ${response.code()}")
                }
            } catch (e: Exception) {
                Log.e("CategoryViewModel", "Exception: ${e.message}")
            }
        }
    }
}

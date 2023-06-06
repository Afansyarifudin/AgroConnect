package com.example.agroconnect.auth

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.agroconnect.Result
import com.example.agroconnect.api.ApiConfig
import com.example.agroconnect.api.ApiService
import com.example.agroconnect.datamodel.LoginRequest
import com.example.agroconnect.datamodel.LoginResponse
import kotlinx.coroutines.launch

class LoginViewModel : ViewModel() {

    private val apiService: ApiService = ApiConfig.createApiService()

    private val _loginResult = MutableLiveData<Result<LoginResponse>>()
    val loginResult: LiveData<Result<LoginResponse>> = _loginResult

    fun login(email: String, password: String) {
        viewModelScope.launch {
            try {
                val request = LoginRequest(email, password)
                Log.d("LoginViewModel", "Sending login request: $request") // Logging the request
                val response = apiService.login(request)
                Log.d("LoginViewModel", "$response")
                if (response.isSuccessful) {
                    val loginResponse = response.body()
                    _loginResult.value = Result.Success(loginResponse)
                } else {
                    _loginResult.value = Result.Error(Exception("Login failed"))
                }
            } catch (e: Exception) {
                _loginResult.value = Result.Error(e)
            }
        }
    }

}



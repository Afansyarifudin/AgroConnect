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
                Log.d("LoginViewModel", "Sending login request: $request")
                val response = apiService.login(request)
//                val responseBody = response.body()?.user

                Log.d("LoginViewModel", "Response: $response")

                if (response.isSuccessful) {
                    val loginResponse = response.body()
                    if (loginResponse != null) {
                        _loginResult.value = Result.Success(loginResponse)
                        Log.d("LoginViewModel", "Berhasil kok")
                    } else {
                        Log.d("LoginViewModel", "Response body is null")
                        _loginResult.value = Result.Error(Exception("Login failed"))
                    }
                } else {
                    Log.d("LoginViewModel", "Response not successful: ${response.code()}")
                    _loginResult.value = Result.Error(Exception("Login failed"))
                }
            } catch (e: Exception) {
                Log.e("LoginViewModel", "Exception: ${e.message}", e)
                _loginResult.value = Result.Error(e)
            }
        }

    }

}



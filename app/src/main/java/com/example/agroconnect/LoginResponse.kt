package com.example.agroconnect

import com.google.gson.annotations.SerializedName

data class LoginResponse(
    @SerializedName("data") val user: List<User>,
    @SerializedName("token") val token: String
)

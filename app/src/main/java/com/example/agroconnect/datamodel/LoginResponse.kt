package com.example.agroconnect.datamodel

import com.google.gson.annotations.SerializedName

data class LoginResponse(
    @SerializedName("data") val user: List<User>,
    @SerializedName("token") val token: String
) {
    val username: String
    get() = user[0].username

    val role: String
        get() = user[0].role
}

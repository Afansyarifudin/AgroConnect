package com.example.agroconnect.datamodel

import com.google.gson.annotations.SerializedName

data class DemandCreateResponse(

    @field:SerializedName("data")
    val data: Demand? = null,

    @field:SerializedName("message")
    val message: String? = null,

    @field:SerializedName("status")
    val status: String? = null
)

package com.example.agroconnect.datamodel

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

data class Product(
    @field:SerializedName("estimate_exp")
    val estimateExp: String? = null,

    @field:SerializedName("amount")
    val amount: Int? = null,

    @field:SerializedName("Category")
    val category: Category? = null,

    @field:SerializedName("User")
    val user: User? = null,

    @field:SerializedName("crop_date")
    val cropDate: String? = null,

    @field:SerializedName("category_id")
    val categoryId: Int? = null,

    @field:SerializedName("user_id")
    val userId: Int? = null,

    @field:SerializedName("name")
    val name: String? = null,

    @field:SerializedName("location")
    val location: String? = null,
)

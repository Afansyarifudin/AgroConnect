package com.example.agroconnect

import kotlinx.parcelize.Parcelize
import android.os.Parcelable

import com.google.gson.annotations.SerializedName

data class ProductResponse(
	val status: String,
	val message: String,
	val data: List<Product>
)

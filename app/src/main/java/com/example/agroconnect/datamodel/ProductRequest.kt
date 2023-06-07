package com.example.agroconnect.datamodel

import com.google.gson.annotations.SerializedName

data class ProductRequest(

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("amount")
	val amount: Int? = null,

	@field:SerializedName("location")
	val location: String? = null,

	@field:SerializedName("crop_date")
	val crop_date: String? = null,

	@field:SerializedName("estimate_exp")
	val estimate_exp: String? = null,

	@field:SerializedName("category_id")
	val category_id: Int? = null


)

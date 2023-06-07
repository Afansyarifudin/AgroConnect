package com.example.agroconnect.datamodel

import com.google.gson.annotations.SerializedName

data class Demand(

	@field:SerializedName("createdAt")
	val createdAt: String? = null,

	@field:SerializedName("amount")
	val amount: Int? = null,

	@field:SerializedName("Category")
	val category: Category? = null,

	@field:SerializedName("User")
	val user: User? = null,

	@field:SerializedName("category_id")
	val categoryId: Int? = null,

	@field:SerializedName("user_id")
	val userId: Int? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("location")
	val location: String? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("updatedAt")
	val updatedAt: String? = null
)

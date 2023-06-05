package com.example.agroconnect

import kotlinx.parcelize.Parcelize
import android.os.Parcelable

import com.google.gson.annotations.SerializedName

@Parcelize
data class AgroResponse(
	@SerializedName("status") val status: String,
	@SerializedName("message") val message: String,
	@SerializedName("data") val data: AgroData
) : Parcelable {
	@Parcelize
	data class AgroData(
		@SerializedName("id") val id: Int,
		@SerializedName("name") val name: String,
		@SerializedName("amount") val amount: Int,
		@SerializedName("location") val location: String,
		@SerializedName("crop_date") val crop_date: String,
		@SerializedName("estimate_exp") val estimate_exp: String,
		@SerializedName("category_id") val category_id: Int,
		@SerializedName("user_id") val user_id: Int,
		@SerializedName("createdAt") val createdAt: String,
		@SerializedName("updatedAt") val updatedAt: String,
		@SerializedName("Category") val Category: AgroCategory,
		@SerializedName("User") val User: AgroUser
	) : Parcelable

	@Parcelize
	data class AgroCategory(
		@SerializedName("id") val id: Int,
		@SerializedName("name") val name: String,
		@SerializedName("createdAt") val createdAt: String,
		@SerializedName("updatedAt") val updatedAt: String
	) : Parcelable

	@Parcelize
	data class AgroUser(
		@SerializedName("id") val id: Int,
		@SerializedName("username") val username: String,
		@SerializedName("email") val email: String,
		@SerializedName("password") val password: String,
		@SerializedName("role") val role: String,
		@SerializedName("createdAt") val createdAt: String,
		@SerializedName("updatedAt") val updatedAt: String
	) : Parcelable
}



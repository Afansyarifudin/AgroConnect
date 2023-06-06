package com.example.agroconnect.datamodel

data class ProductResponse(
	val status: String,
	val message: String,
	val data: List<Product>
)

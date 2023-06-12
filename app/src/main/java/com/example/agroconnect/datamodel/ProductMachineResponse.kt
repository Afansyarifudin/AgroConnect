package com.example.agroconnect.datamodel

import com.google.gson.annotations.SerializedName

data class ProductMachineResponse(

	@field:SerializedName("Response")
	val response: List<ProductMachine?>? = null
)



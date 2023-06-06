package com.example.agroconnect.datamodel

import com.example.agroconnect.trade.Category

data class CategoryResponse(
    val message: String,
    val data: List<Category>
)

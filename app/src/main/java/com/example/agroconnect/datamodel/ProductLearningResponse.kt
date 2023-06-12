package com.example.agroconnect.datamodel

data class ProductLearningResponse(

    val id: Int,
    val name: String,
    val quantity: Int,
    val location: String,
    val startDate: String,
    val endDate: String,
    val category: Int,
    val sellerId: Int,
    val createdAt: String,
    val updatedAt: String

)


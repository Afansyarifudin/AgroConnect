package com.example.agroconnect.datamodel

data class Product(
    val id: Int,
    val name: String,
    val amount: Int,
    val location: String,
    val crop_date: String,
    val estimate_exp: String,
    val category_id: Int,
    val user_id: Int,
    val createdAt: String,
    val updatedAt: String
)

package com.example.agroconnect.datamodel

import com.google.gson.annotations.SerializedName

data class ProductMachine(

    @field:SerializedName("Score")
    val score: Any? = null,

    @field:SerializedName("Commodity_1")
    val commodity1: Int? = null,

    @field:SerializedName("Long")
    val long: Any? = null,

    @field:SerializedName("Commodity_6")
    val commodity6: Int? = null,

    @field:SerializedName("Commodity_2")
    val commodity2: Int? = null,

    @field:SerializedName("Commodity_3")
    val commodity3: Int? = null,

    @field:SerializedName("Lat")
    val lat: Any? = null,

    @field:SerializedName("Commodity_4")
    val commodity4: Int? = null,

    @field:SerializedName("Location_rank")
    val locationRank: Int? = null,

    @field:SerializedName("Name")
    val name: String? = null,

    @field:SerializedName("Commodity_5")
    val commodity5: Int? = null
)

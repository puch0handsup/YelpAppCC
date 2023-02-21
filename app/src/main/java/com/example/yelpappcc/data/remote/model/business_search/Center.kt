package com.example.yelpappcc.data.remote.model.business_search


import com.google.gson.annotations.SerializedName

data class Center(
    @SerializedName("latitude")
    val latitude: Double? = null,
    @SerializedName("longitude")
    val longitude: Double? = null
)
package com.example.yelpappcc.data.remote.model.business_search


import com.google.gson.annotations.SerializedName

data class Region(
    @SerializedName("center")
    val center: Center? = null
)
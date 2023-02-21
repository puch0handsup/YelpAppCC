package com.example.yelpappcc.data.remote.model.business_search


import com.google.gson.annotations.SerializedName

data class Category(
    @SerializedName("alias")
    val alias: String? = null,
    @SerializedName("title")
    val title: String? = null
)
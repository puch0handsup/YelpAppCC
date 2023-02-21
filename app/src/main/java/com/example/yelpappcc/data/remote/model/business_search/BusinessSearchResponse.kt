package com.example.yelpappcc.data.remote.model.business_search


import com.google.gson.annotations.SerializedName

data class BusinessSearchResponse(
    @SerializedName("businesses")
    val businesses: List<BusinessItem?>? = null,
    @SerializedName("region")
    val region: Region? = null,
    @SerializedName("total")
    val total: Int? = null
)
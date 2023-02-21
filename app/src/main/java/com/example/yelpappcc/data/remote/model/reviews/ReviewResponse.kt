package com.example.yelpappcc.data.remote.model.reviews


import com.google.gson.annotations.SerializedName

data class ReviewResponse(
    @SerializedName("possible_languages")
    val possibleLanguages: List<String?>? = null,
    @SerializedName("reviewItems")
    val reviewItems: List<ReviewItem?>? = null,
    @SerializedName("total")
    val total: Int? = null
)
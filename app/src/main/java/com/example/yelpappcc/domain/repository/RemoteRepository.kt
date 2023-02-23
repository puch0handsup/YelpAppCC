package com.example.yelpappcc.domain.repository

import android.location.Location
import com.example.yelpappcc.data.remote.model.business_search.BusinessSearchResponse
import com.example.yelpappcc.data.remote.model.reviews.ReviewResponse
import retrofit2.Response

interface RemoteRepository {
    suspend fun getNearbyRestaurants(latitude: String, longitude: String) : Response<BusinessSearchResponse>
    suspend fun getRestaurantReviewsById(reviewId : String) : Response<ReviewResponse>
}
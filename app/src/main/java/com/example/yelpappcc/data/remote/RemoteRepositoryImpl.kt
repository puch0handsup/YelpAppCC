package com.example.yelpappcc.data.remote

import com.example.yelpappcc.data.remote.model.business_search.BusinessSearchResponse
import com.example.yelpappcc.data.remote.model.reviews.ReviewResponse
import com.example.yelpappcc.domain.repository.RemoteRepository
import retrofit2.Response
import javax.inject.Inject

class RemoteRepositoryImpl @Inject constructor(
    private val serviceApi: YelpServiceApi
) : RemoteRepository {

    override suspend fun getNearbyRestaurants(
        latitude: String,
        longitude: String
    ): Response<BusinessSearchResponse> = serviceApi.getNearbyRestaurants(latitude, longitude)

    override suspend fun getRestaurantReviewsById(reviewId: String): Response<ReviewResponse> =
        serviceApi.getRestaurantReviewsById(reviewId)
}
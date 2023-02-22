package com.example.yelpappcc.data.remote

import com.example.yelpappcc.data.remote.model.business_search.BusinessSearchResponse
import com.example.yelpappcc.data.remote.model.reviews.ReviewResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface YelpServiceApi {

    @GET(BUSINESSES_SEARCH)
    suspend fun getNearbyRestaurants(
        @Query("latitude") latitude : String,
        @Query("longitude") longitude : String,
        @Query("attributes") attributes : String = "hot_and_new",
        @Query("sort_by") sort_by : String = "best_match",
        @Query("limit") limit : Int = 20
    ) : Response<BusinessSearchResponse>

    @GET(BUSINESS_ID_PATH + BUSINESS_REVIEWS)
    suspend fun getRestaurantReviewsById(
        @Path("id") id : String,
        @Query("sort_by") sort_by : String = "yelp_sort",
        @Query("limit") limit : Int = 20
    ) : Response<ReviewResponse>

    companion object {
        const val BASE_URL = "https://api.yelp.com/v3/businesses/"
        private const val BUSINESSES_SEARCH = "search"
        private const val BUSINESS_ID_PATH = "{id}"
        private const val BUSINESS_REVIEWS = "/reviews"
    }
}
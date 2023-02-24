package com.example.yelpappcc.domain.model

import com.example.yelpappcc.data.local.model.ReviewEntity

data class Review(
    val id: String?,
    val businessId: String?,
    val userName: String?,
    val userImageUrl: String?,
    val rating: Int?,
    val text: String?,
    val timeCreated: String?,
)

fun List<ReviewEntity>?.mapToReview(): List<Review>? {
    return this?.map {
        Review(
            id = it.id,
            businessId = it.businessId,
            userName = it.userName,
            userImageUrl = it.userImageUrl,
            rating = it.rating,
            text = it.text,
            timeCreated = it.timeCreated
        )
    }
}
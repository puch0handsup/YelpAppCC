package com.example.yelpappcc.data.local.model

import com.example.yelpappcc.data.remote.model.reviews.ReviewItem

data class ReviewEntity(
    val id: String?,
    val userName: String?,
    val userImageUrl: String?,
    val rating: Int?,
    val text: String?
)

fun List<ReviewItem>?.mapToReview(): List<ReviewEntity?>? {
    return this?.map {
        ReviewEntity(
            id = it.id,
            userName = it.user?.name,
            userImageUrl = it.user?.imageUrl,
            rating = it.rating,
            text = it.text
        )
    }
}

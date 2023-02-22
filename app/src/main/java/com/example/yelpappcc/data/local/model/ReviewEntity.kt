package com.example.yelpappcc.data.local.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.yelpappcc.data.remote.model.reviews.ReviewItem

@Entity(tableName = "reviews")
data class ReviewEntity(
    @PrimaryKey val id: String,
    val businessId: String?,
    val userName: String?,
    val userImageUrl: String?,
    val rating: Int?,
    val text: String?
)

fun List<ReviewItem>?.mapToReview(businessId: String?): List<ReviewEntity?>? {
    return this?.map {
        ReviewEntity(
            id = it.id!!,
            businessId = businessId,
            userName = it.user?.name,
            userImageUrl = it.user?.imageUrl,
            rating = it.rating,
            text = it.text
        )
    }
}

package com.example.yelpappcc.domain.use_cases

import android.util.Log
import com.example.yelpappcc.data.local.model.ReviewEntity
import com.example.yelpappcc.data.local.model.mapToReview
import com.example.yelpappcc.domain.model.Review
import com.example.yelpappcc.domain.model.mapToReview
import com.example.yelpappcc.domain.repository.LocalRepository
import com.example.yelpappcc.domain.repository.RemoteRepository
import com.example.yelpappcc.utils.NetworkState
import com.example.yelpappcc.utils.UIState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

private const val TAG = "GetBusinessReviewsByBusinessId"
class GetBusinessReviewsByBusinessId @Inject constructor(
    private val remoteRepository: RemoteRepository,
    private val localRepository: LocalRepository,
    private val networkState: NetworkState
) {
    operator fun invoke(businessId: String) : Flow<UIState<List<Review>>> = flow {
        emit(UIState.LOADING)
        try {
            val localReviews = localRepository.getReviewsByBusinessId(businessId)
            if (localReviews.isNullOrEmpty()){
                if (networkState.isInternetEnabled()) {
                    val response = remoteRepository.getRestaurantReviewsById(businessId)
                    if (response.isSuccessful) {
                        response.body()?.let {
                            Log.d(TAG, "invoke: ${it.reviews}")
                            localRepository.insertReviews(it.reviews.mapToReview(businessId) as List<ReviewEntity>?)
                            val reviews = localRepository.getReviewsByBusinessId(businessId).mapToReview()
                            emit(UIState.SUCCESS(reviews))
                        } ?: throw Exception("Response body is null")
                    } else throw Exception(response.errorBody()?.string())
                } else throw Exception("No reviews associated and network unavailable")
            } else {
                emit(UIState.SUCCESS(localReviews.mapToReview()))
            }
        } catch (e : Exception) {
            emit(UIState.ERROR(e))
        }
    }
}
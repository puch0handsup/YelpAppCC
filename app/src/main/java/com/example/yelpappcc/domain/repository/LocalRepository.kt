package com.example.yelpappcc.domain.repository

import com.example.yelpappcc.data.local.model.BusinessEntity
import com.example.yelpappcc.data.local.model.ReviewEntity
import com.example.yelpappcc.data.local.model.SearchLocationHistoryEntity

interface LocalRepository {
    suspend fun insertBusinesses(businesses : List<BusinessEntity>?)
    suspend fun insertReviews(businessReviews : List<ReviewEntity>?)
    suspend fun insertSearchedLocation(searchedLocation : SearchLocationHistoryEntity) : Long
    suspend fun getSearchedLocations(): List<SearchLocationHistoryEntity>?
    suspend fun getSearchedLocationById(id: Int) : SearchLocationHistoryEntity
    suspend fun getBusinessesByIdList(businessesIdList: List<String?>?): List<BusinessEntity>
    suspend fun getReviewsByBusinessId(businessId: String) : List<ReviewEntity>?
}
package com.example.yelpappcc.data.local

import com.example.yelpappcc.data.local.model.BusinessEntity
import com.example.yelpappcc.data.local.model.ReviewEntity
import com.example.yelpappcc.data.local.model.SearchLocationHistoryEntity
import com.example.yelpappcc.domain.repository.LocalRepository
import javax.inject.Inject

class LocalRepositoryImpl @Inject constructor(
    private val dao: YelpDAO
) : LocalRepository {

    override suspend fun insertBusinesses(businesses: List<BusinessEntity>?) {
        dao.insertBusinesses(businesses)
    }

    override suspend fun insertReviews(businessReviews: List<ReviewEntity>?) {
        dao.insertReviews(businessReviews)
    }

    override suspend fun insertSearchedLocation(searchedLocation: SearchLocationHistoryEntity): Long =
        dao.insertSearchedLocation(searchedLocation)

    override suspend fun getSearchedLocations(): List<SearchLocationHistoryEntity> =
        dao.getSearchedLocations()

    override suspend fun getSearchedLocationById(id: Int): SearchLocationHistoryEntity =
        dao.getSearchLocationHistoryById(id)

    override suspend fun getBusinessesByIdList(businessesIdList: List<String?>?): List<BusinessEntity> =
        dao.getBusinessesByIdList(businessesIdList)

    override suspend fun getReviewsByBusinessId(businessId: String): List<ReviewEntity>? =
        dao.getReviewsByBusinessId(businessId)

}
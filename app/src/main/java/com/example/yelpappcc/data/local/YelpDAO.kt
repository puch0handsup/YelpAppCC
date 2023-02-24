package com.example.yelpappcc.data.local

import androidx.room.*
import com.example.yelpappcc.data.local.model.BusinessEntity
import com.example.yelpappcc.data.local.model.ReviewEntity
import com.example.yelpappcc.data.local.model.SearchLocationHistoryEntity

@Dao
interface YelpDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBusinesses(businesses : List<BusinessEntity>?)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertReviews(businessReviews : List<ReviewEntity>?)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSearchedLocation(searchedLocation : SearchLocationHistoryEntity) : Long

    @Query("SELECT * from location_history")
    suspend fun getSearchedLocations(): List<SearchLocationHistoryEntity>

    @Query("SELECT * from location_history WHERE id LIKE :id")
    suspend fun getSearchLocationHistoryById(id: Int) : SearchLocationHistoryEntity

    @Query("SELECT * from businesses WHERE id IN (:businessesIdList)")
    suspend fun getBusinessesByIdList(businessesIdList: List<String?>?): List<BusinessEntity>

    @Query("SELECT * from reviews WHERE businessId LIKE :businessId")
    suspend fun getReviewsByBusinessId(businessId: String) : List<ReviewEntity>

}
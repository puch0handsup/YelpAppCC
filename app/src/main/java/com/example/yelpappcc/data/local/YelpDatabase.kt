package com.example.yelpappcc.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.yelpappcc.data.local.model.BusinessEntity
import com.example.yelpappcc.data.local.model.ReviewEntity
import com.example.yelpappcc.data.local.model.SearchLocationHistoryEntity

@Database(
    entities = [
        BusinessEntity::class,
        ReviewEntity::class,
        SearchLocationHistoryEntity::class
    ],
    version = 1
)
abstract class YelpDatabase : RoomDatabase() {
    abstract val dao : YelpDAO
}
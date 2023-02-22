package com.example.yelpappcc.data.local.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.yelpappcc.data.remote.model.business_search.BusinessItem
import com.google.gson.Gson

@Entity (tableName = "location_history")
class SearchLocationHistoryEntity (
    @PrimaryKey(autoGenerate = true) val id: Int? = 0,
    val searchLocationName : String? = "Search #: $id",
    val businessIdList : String
)

fun List<BusinessItem>.addLocationHistoryToDB() : SearchLocationHistoryEntity {
    val gson = Gson()
    return SearchLocationHistoryEntity(
        businessIdList = gson.toJson(this.map { it.id })
    )
}
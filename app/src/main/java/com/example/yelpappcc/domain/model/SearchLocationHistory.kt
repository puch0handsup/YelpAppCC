package com.example.yelpappcc.domain.model

import com.example.yelpappcc.data.local.model.SearchLocationHistoryEntity
import com.example.yelpappcc.data.remote.model.business_search.BusinessItem
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

data class SearchLocationHistory(
    val id: Int?,
    val searchLocationName : String?,
    val businessIdList : List<String>?
)

fun SearchLocationHistoryEntity.mapToLocationHistory() : SearchLocationHistory {
    val gson = Gson()
    val stringType = object : TypeToken<List<String>>() {}.type

    return SearchLocationHistory(
        id = this.id,
        searchLocationName = this.searchLocationName,
        businessIdList = gson.fromJson(this.businessIdList, stringType)
    )
}

fun List<SearchLocationHistoryEntity>.mapToLocationHistoryList() : List<SearchLocationHistory> =
    this.map {
        val gson = Gson()
        val stringType = object : TypeToken<List<String>>() {}.type

        SearchLocationHistory(
            id = it.id,
            searchLocationName = it.searchLocationName,
            businessIdList = gson.fromJson(it.businessIdList, stringType)
        )
    }

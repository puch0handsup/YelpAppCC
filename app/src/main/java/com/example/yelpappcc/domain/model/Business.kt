package com.example.yelpappcc.domain.model

import com.example.yelpappcc.data.local.model.BusinessEntity
import com.example.yelpappcc.data.remote.model.business_search.Location
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

data class Business(
    val id: String?,
    val name: String?,
    val price: String?,
    val imageUrl: String?,
    val rating: Double?,
    val distance: Double?,
    val phone: String?,
    val location: Location?,
    val reviewCount: Int?,
    var isFavorite : Boolean
)

fun List<BusinessEntity>?.mapToBusiness(): List<Business>? {
    return this?.map {
        val gson = Gson()
        val locationType = object : TypeToken<Location>() {}.type
        Business(
            id = it.id,
            name = it.name,
            price = it.price,
            imageUrl = it.imageUrl,
            rating = it.rating,
            distance = it.distance,
            phone = it.phone,
            location = gson.fromJson(it.location, locationType),
            reviewCount = it.reviewCount,
            isFavorite = it.isFavorite
        )
    }
}
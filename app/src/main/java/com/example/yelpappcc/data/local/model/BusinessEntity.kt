package com.example.yelpappcc.data.local.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.yelpappcc.data.remote.model.business_search.BusinessItem
import com.google.gson.Gson

@Entity(tableName = "businesses")
data class BusinessEntity(
    @PrimaryKey val id : String,
    val name: String?,
    val price: String?,
    val imageUrl: String?,
    val rating: Double?,
    val distance: Double?,
    val phone: String?,
    val location: String?,
    var isFavorite: Boolean = false
)

fun List<BusinessItem?>?.mapToBusinessEntity(): List<BusinessEntity>? {
    return this?.map {
        val gson = Gson()
        BusinessEntity(
            id = it?.id!!,
            name = it.name,
            price = it.price,
            imageUrl = it.imageUrl,
            rating = it.rating,
            distance = it.distance,
            phone = it.phone,
            location = gson.toJson(it.location)
        )
    }
}


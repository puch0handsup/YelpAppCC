package com.example.yelpappcc.domain.use_cases

import android.location.Location
import com.example.yelpappcc.data.local.model.addLocationHistoryToDB
import com.example.yelpappcc.data.local.model.mapToBusinessEntity
import com.example.yelpappcc.domain.model.Business
import com.example.yelpappcc.domain.model.mapToBusiness
import com.example.yelpappcc.domain.model.mapToLocationHistory
import com.example.yelpappcc.domain.repository.LocalRepository
import com.example.yelpappcc.domain.repository.RemoteRepository
import com.example.yelpappcc.utils.NetworkState
import com.example.yelpappcc.utils.UIState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

private const val TAG = "GetBusinessesByLocation"
class GetBusinessesByLocation @Inject constructor(
    private val localRepository: LocalRepository,
    private val remoteRepository: RemoteRepository,
    private val networkState: NetworkState
) {
    operator fun invoke(location: Location): Flow<UIState<List<Business>>> = flow {
        emit(UIState.LOADING)
        if (networkState.isInternetEnabled()) {
            try {
                val response = remoteRepository.getNearbyRestaurants(location.latitude.toString(), location.longitude.toString())
                if (response.isSuccessful){
                    response.body()?.let {
                        // inserting businesses to db
                        localRepository.insertBusinesses(it.businesses.mapToBusinessEntity())
                        // inserting the location history to db, and obtaining its id
                        val businessHistoryId = localRepository.insertSearchedLocation(it.businesses.addLocationHistoryToDB())
                        // retrieving the location history by using the id previously obtained
                        val locationHistoryEntity = localRepository.getSearchedLocationById(businessHistoryId.toInt())
                        // using this function to convert the json string saved to db, into a list of Ids associated with businesses
                        val locationHistory = locationHistoryEntity.mapToLocationHistory()
                        // after obtaining the list of IDs, search for the business list associated with those IDs.
                        val businesses = localRepository.getBusinessesByIdList(locationHistory.businessIdList)
                        // map entity to domain and emit
                        emit(UIState.SUCCESS(businesses.mapToBusiness()))
                    } ?: throw Exception("null response body")
                } else throw Exception(response.errorBody().toString())
            } catch (e : Exception) {
                emit(UIState.ERROR(e))
            }
        } else {
            emit(UIState.ERROR(Exception("Internet is not enabled")))
        }
    }
}
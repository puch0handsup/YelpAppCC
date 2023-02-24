package com.example.yelpappcc.domain.use_cases

import com.example.yelpappcc.domain.model.SearchLocationHistory
import com.example.yelpappcc.domain.model.mapToLocationHistoryList
import com.example.yelpappcc.domain.repository.LocalRepository
import com.example.yelpappcc.utils.UIState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetSearchHistory @Inject constructor(
    private val localRepository: LocalRepository
) {
    operator fun invoke() : Flow<UIState<List<SearchLocationHistory>>> = flow {
        try {
            val localHistory = localRepository.getSearchedLocations()
            localHistory?.let { history ->
                emit(UIState.SUCCESS(history.mapToLocationHistoryList()))
            } ?: throw Exception("Null location history")
        } catch (e : Exception) {
            emit(UIState.ERROR(e))
        }
    }
}
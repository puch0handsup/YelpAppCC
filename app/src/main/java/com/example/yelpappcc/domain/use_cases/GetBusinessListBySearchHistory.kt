package com.example.yelpappcc.domain.use_cases

import com.example.yelpappcc.data.local.model.BusinessEntity
import com.example.yelpappcc.domain.model.Business
import com.example.yelpappcc.domain.model.mapToBusiness
import com.example.yelpappcc.domain.repository.LocalRepository
import com.example.yelpappcc.utils.UIState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetBusinessListBySearchHistory @Inject constructor(
    private val localRepository: LocalRepository
) {
    operator fun invoke(businessIds : List<String>) : Flow<UIState<List<Business>>> = flow {
        val businesses : List<BusinessEntity> = localRepository.getBusinessesByIdList(businessIds)
        emit(UIState.SUCCESS(businesses.mapToBusiness()))
    }
}
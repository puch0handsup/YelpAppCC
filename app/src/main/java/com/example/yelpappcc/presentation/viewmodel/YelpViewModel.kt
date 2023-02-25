package com.example.yelpappcc.presentation.viewmodel

import android.location.Location
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.yelpappcc.domain.model.Business
import com.example.yelpappcc.domain.model.Review
import com.example.yelpappcc.domain.model.SearchLocationHistory
import com.example.yelpappcc.domain.use_cases.GetBusinessListBySearchHistory
import com.example.yelpappcc.domain.use_cases.GetBusinessReviewsByBusinessId
import com.example.yelpappcc.domain.use_cases.GetBusinessesByLocation
import com.example.yelpappcc.domain.use_cases.GetSearchHistory
import com.example.yelpappcc.utils.UIState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val TAG = "YelpViewModel"
@HiltViewModel
class YelpViewModel @Inject constructor(
    private val getBusinessesByLocation: GetBusinessesByLocation,
    private val getBusinessReviewsByBusinessId: GetBusinessReviewsByBusinessId,
    private val getSearchHistory: GetSearchHistory,
    private val getBusinessListBySearchHistory: GetBusinessListBySearchHistory
) : ViewModel() {

    var location : Location? = null
    var arePermsGranted : Boolean = false
    var selectedBusiness : Business ? = null
    var selectedBusinessHistory : Business ? = null
    var businessHistoryIdList : List<String> = listOf()

    private val _businesses : MutableLiveData<UIState<List<Business>>> = MutableLiveData(UIState.LOADING)
    val businesses : MutableLiveData<UIState<List<Business>>> get() = _businesses

    private val _reviews : MutableLiveData<UIState<List<Review>>> = MutableLiveData(UIState.LOADING)
    val reviews : MutableLiveData<UIState<List<Review>>> get() = _reviews

    private val _history : MutableLiveData<UIState<List<SearchLocationHistory>>> = MutableLiveData(UIState.LOADING)
    val history : MutableLiveData<UIState<List<SearchLocationHistory>>> get() = _history

    private val _businessHistory : MutableLiveData<UIState<List<Business>>> = MutableLiveData(UIState.LOADING)
    val businessHistory : MutableLiveData<UIState<List<Business>>> get() = _businessHistory

    fun getBusinessesList() {
        viewModelScope.launch {
            location?.let { location ->
                getBusinessesByLocation(location).collect {
                    _businesses.postValue(it)
                }
            } ?: run { Log.e(TAG, "getBusinessesList: Location is Null") }
        }
    }

    fun getReviews(businessId : String) {
        viewModelScope.launch {
            getBusinessReviewsByBusinessId(businessId).collect {
                _reviews.postValue(it)
            }
        }
    }

    fun getHistory() {
        viewModelScope.launch {
            getSearchHistory().collect {
                _history.postValue(it)
            }
        }
    }

    fun getBusinessesByHistory() {
        viewModelScope.launch {
            getBusinessListBySearchHistory(businessHistoryIdList).collect {
                Log.d(TAG, "getBusinessesByHistory: $it")
                _businessHistory.postValue(it)
            }
        }
    }


}
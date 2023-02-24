package com.example.yelpappcc.presentation.viewmodel

import android.location.Location
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.yelpappcc.domain.model.Business
import com.example.yelpappcc.domain.model.Review
import com.example.yelpappcc.domain.use_cases.GetBusinessReviewsByBusinessId
import com.example.yelpappcc.domain.use_cases.GetBusinessesByLocation
import com.example.yelpappcc.utils.UIState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val TAG = "YelpViewModel"
@HiltViewModel
class YelpViewModel @Inject constructor(
    private val getBusinessesByLocation: GetBusinessesByLocation,
    private val getBusinessReviewsByBusinessId: GetBusinessReviewsByBusinessId
) : ViewModel() {

    var location : Location? = null
    val arePermsGranted : Boolean = false
    var selectedBusiness : Business ? = null

    private val _businesses : MutableLiveData<UIState<List<Business>>> = MutableLiveData(UIState.LOADING)
    val businesses : MutableLiveData<UIState<List<Business>>> get() = _businesses

    private val _reviews : MutableLiveData<UIState<List<Review>>> = MutableLiveData(UIState.LOADING)
    val reviews : MutableLiveData<UIState<List<Review>>> get() = _reviews

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
}
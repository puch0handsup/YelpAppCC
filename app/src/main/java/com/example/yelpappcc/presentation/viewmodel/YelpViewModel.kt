package com.example.yelpappcc.presentation.viewmodel

import android.location.Location
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.yelpappcc.domain.model.Business
import com.example.yelpappcc.utils.UIState

class YelpViewModel : ViewModel() {

    var location : Location? = null
    val arePermsGranted : Boolean = false

    private val _businesses : MutableLiveData<UIState<List<Business>>> = MutableLiveData(UIState.LOADING)
    val businesses : MutableLiveData<UIState<List<Business>>> get() = _businesses


}
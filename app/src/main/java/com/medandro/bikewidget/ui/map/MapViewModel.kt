package com.medandro.bikewidget.ui.map

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MapViewModel : ViewModel() {
    private val _text =
        MutableLiveData<String>().apply {
            value = "지도 프래그먼트"
        }
    val text: LiveData<String> = _text
}

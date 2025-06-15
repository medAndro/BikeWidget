package com.medandro.bikewidget.ui.favorite

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class FavoriteViewModel : ViewModel() {
    private val _text =
        MutableLiveData<String>().apply {
            value = "즐겨찾기 프래그먼트"
        }
    val text: LiveData<String> = _text
}

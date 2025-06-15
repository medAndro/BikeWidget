package com.medandro.bikewidget.ui.map

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.NaverMap
import com.naver.maps.map.overlay.Marker

class MapViewModel : ViewModel() {
    private val _naverMap = MutableLiveData<NaverMap>()
    val naverMap: LiveData<NaverMap> = _naverMap

    fun onMapReady(naverMap: NaverMap) {
        _naverMap.value = naverMap
        addMarkerAtCurrentLocation()
    }

    fun addMarkerAtCurrentLocation() {
        val currentMap = naverMap.value ?: return

        val marker = Marker()
        marker.position = LatLng(37.5670135, 126.9783740)
        marker.map = currentMap
    }
}

package com.medandro.bikewidget.ui.map

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.CameraUpdate
import com.naver.maps.map.LocationTrackingMode
import com.naver.maps.map.NaverMap
import com.naver.maps.map.overlay.Marker

class MapViewModel : ViewModel() {
    private val _naverMap = MutableLiveData<NaverMap>()
    val naverMap: LiveData<NaverMap> = _naverMap

    fun onMapReady(naverMap: NaverMap) {
        _naverMap.value = naverMap
        addMarkerAtCurrentLocation()
        naverMap.locationOverlay.isVisible = false
    }

    fun addMarkerAtCurrentLocation() {
        val currentMap = naverMap.value ?: return

        val marker = Marker()
        marker.position = LatLng(37.5670135, 126.9783740)
        marker.map = currentMap
    }

    fun moveCamera(position: LatLng) {
        val cameraUpdate = CameraUpdate.scrollTo(position)
        naverMap.value?.moveCamera(cameraUpdate)
    }

    fun showUserLocationUI() {
        naverMap.value?.uiSettings?.isLocationButtonEnabled = true
        naverMap.value?.locationOverlay?.isVisible = true
    }

    fun hideUserLocationUI() {
        naverMap.value?.uiSettings?.isLocationButtonEnabled = false
    }

    fun movePosition(position: LatLng) {
        naverMap.value?.locationOverlay?.position = position
    }

    fun setTrackingMode(trackingMode: LocationTrackingMode) {
        naverMap.value?.locationTrackingMode = trackingMode
    }
}

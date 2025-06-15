package com.medandro.bikewidget.ui.map

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.CameraPosition
import com.naver.maps.map.LocationTrackingMode
import com.naver.maps.map.NaverMap
import com.naver.maps.map.overlay.Marker

class MapViewModel : ViewModel() {
    private val _naverMap = MutableLiveData<NaverMap>()
    val naverMap: LiveData<NaverMap> = _naverMap

    var lastCameraPosition: CameraPosition? = null

    fun onMapReady(naverMap: NaverMap) {
        _naverMap.value = naverMap
        addMarkerAtCurrentLocation()
        lastCameraPosition?.let { position ->
            naverMap.cameraPosition = position
        }
        setupMap()
    }

    fun setupMap() {
        naverMap.value?.uiSettings?.isLocationButtonEnabled = false
        naverMap.value?.locationOverlay?.isVisible = false
    }

    fun addMarkerAtCurrentLocation() {
        val currentMap = naverMap.value ?: return

        val marker = Marker()
        marker.position = LatLng(37.5670135, 126.9783740)
        marker.map = currentMap
    }

    fun setTrackingMode(trackingMode: LocationTrackingMode) {
        naverMap.value?.locationTrackingMode = trackingMode
    }

    fun saveCameraPosition() {
        lastCameraPosition = naverMap.value?.cameraPosition
    }
}

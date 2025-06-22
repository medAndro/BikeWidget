package com.medandro.bikewidget.ui.map

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.medandro.bikewidget.data.station.StationRepository
import com.medandro.bikewidget.domain.Station
import com.naver.maps.map.CameraPosition
import kotlinx.coroutines.launch

class MapViewModel(
    private val stationRepository: StationRepository,
) : ViewModel() {
    private val _stations = MutableLiveData<List<Station>>()
    val stations: LiveData<List<Station>> = _stations

    private val _selectedStations = MutableLiveData<Station?>()
    val selectedStations: LiveData<Station?> = _selectedStations

    var lastCameraPosition: CameraPosition? = null

    init {
        fetchStations()
    }

    private fun fetchStations() {
        viewModelScope.launch {
            val result = stationRepository.getAllSeoulBikeStations()
            result
                .onSuccess { stationList ->
                    _stations.value = stationList
                }.onFailure { throwable ->
                    Log.d("요청 실패", "$throwable")
                }
        }
    }

    fun stationSelect(station: Station) {
        when {
            station == selectedStations.value -> _selectedStations.value = null
            else -> {
                _selectedStations.value = station
            }
        }
    }

    fun saveCameraPosition(cameraPosition: CameraPosition?) {
        lastCameraPosition = cameraPosition
    }
}

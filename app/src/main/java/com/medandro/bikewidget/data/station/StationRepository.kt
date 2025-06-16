package com.medandro.bikewidget.data.station

import com.medandro.bikewidget.data.station.dto.SeoulBikeStation

interface StationRepository {
    suspend fun getAllSeoulBikeStations(): Result<List<SeoulBikeStation>>
}
